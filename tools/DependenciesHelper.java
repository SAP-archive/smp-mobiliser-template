/**
 */
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Result;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.Text;
import org.xml.sax.SAXException;

public class DependenciesHelper {

	private static final String MANDATORY_ZIP_ENTRY_PREFIX = "WEB-INF/osgi/bundles";
	private static final String BASE_DIR = "${basedir}";
	private static final String DEPENDENCY_PREFIX = "/repo/target/war/";

	public static void main(String[] args) {
		if (args.length != 2) {
			System.err.println("Usage: java "
					+ DependenciesHelper.class.getName()
					+ " <mobiliser war file> <project root dir>");
			System.exit(1);
		}
		List<String> jars = null;
		try {
			jars = readJars(args[0]);
		} catch (IOException e) {
			System.err.println("Could not open Mobiliser war file: " + e);
			System.exit(2);
		}

		try {
			modifyPoms(args[1], jars, 0);

		} catch (IOException e) {
			System.err
					.println("File access exception when working on pom files: "
							+ e);
			System.exit(3);
		} catch (Exception e) {
			System.err.println("Exception while working pom files: " + e);
			System.exit(4);
		}

		System.out.println("pom files modified successfully.");

	}

	private static void modifyPoms(String root, List<String> jars, int depth)
			throws IOException, XPathExpressionException,
			ParserConfigurationException, SAXException, TransformerException {

		File path = new File(root);
		if (!path.isDirectory()) {
			throw new IOException(root + " is not a directory.");
		}
		File[] files = path.listFiles();
		for (File file : files) {
			if (file.isDirectory())
				modifyPoms(file.getAbsolutePath(), jars, depth + 1);
			if ("pom.xml".equals(file.getName())) {
				System.out.println("found pom.xml at location "
						+ file.getAbsolutePath());
				modifyPom(file, jars, depth);
			}
		}
	}

	private static void modifyPom(File pomFile, List<String> jars, int depth)
			throws ParserConfigurationException, SAXException, IOException,
			XPathExpressionException, TransformerException {
		final XPathFactory xPathFactory = XPathFactory.newInstance();
		final XPath xpath = xPathFactory.newXPath();

		final DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory
				.newInstance();
		docBuilderFactory.setIgnoringElementContentWhitespace(true);
		final DocumentBuilder docBuilder = docBuilderFactory
				.newDocumentBuilder();
		final Document base = docBuilder.parse(pomFile);

		String ex = "/project/dependencies/dependency";
		XPathExpression expression = xpath.compile(ex);

		final NodeList dependencies = (NodeList) expression.evaluate(base,
				XPathConstants.NODESET);

		if (dependencies == null || dependencies.getLength() == 0)
			return;

		boolean modified = false;
		for (int f = 0; f < dependencies.getLength(); f++) {
			Node depNode = dependencies.item(f);
			NodeList children = depNode.getChildNodes();
			Dependency dependency = new Dependency();
			for (int i = 0; i < children.getLength(); i++) {
				if ("artifactId".equals(children.item(i).getNodeName()))
					dependency.artifactId = ((Element) children.item(i))
							.getTextContent().trim();
				;
				if ("classifier".equals(children.item(i).getNodeName()))
					dependency.classifier = ((Element) children.item(i))
							.getTextContent().trim();
				if ("type".equals(children.item(i).getNodeName())) {
					dependency.type = ((Element) children.item(i))
							.getTextContent().trim();
					if ("test-jar".equals(dependency.type)) {
						dependency.type = "jar";
						dependency.classifier = "tests";
					}
				}
			}
			if (dependency.artifactId != null) {
				String artifact = findArtifact(dependency, jars, depth);
				if (artifact == null) {
					System.out
							.println("    Did not find following dependency in Mobiliser product: "
									+ dependency);
				} else {
					modified = true;
					System.out.println("    Converting artifact: "
							+ dependency.artifactId);

					boolean foundVersion = false;
					boolean foundScope = false;
					boolean foundSystemPath = false;
					for (int i = 0; i < children.getLength(); i++) {
						if ("version".equals(children.item(i).getNodeName())) {
							((Element) children.item(i))
									.setTextContent("IGNORED");
							foundVersion = true;
						} else if ("systemPath".equals(children.item(i)
								.getNodeName())) {
							((Element) children.item(i))
									.setTextContent(artifact);
							foundSystemPath = true;
						} else if ("scope".equals(children.item(i)
								.getNodeName())) {
							// even overwrite "test"
							// if
							// (!"test".equals(children.item(i).getNodeValue()))
							((Element) children.item(i))
									.setTextContent("system");
							foundScope = true;
						}
					}
					if (!foundVersion) {
						Element newnode = base.createElement("version");
						newnode.setTextContent("IGNORED");
						depNode.appendChild(newnode);
						Text textNode = base.createTextNode("\n      ");
						depNode.appendChild(textNode);
					}
					if (!foundScope) {
						Element newnode = base.createElement("scope");
						newnode.setTextContent("system");
						depNode.appendChild(newnode);
						Text textNode = base.createTextNode("\n      ");
						depNode.appendChild(textNode);
					}
					if (!foundSystemPath) {
						Element newnode = base.createElement("systemPath");
						newnode.setTextContent(artifact);
						depNode.appendChild(newnode);
						Text textNode = base.createTextNode("\n      ");
						depNode.appendChild(textNode);
					}
				}
			}
		}

		if (modified) {
			// write the pom back into the file system
			final TransformerFactory transformerFactory = TransformerFactory
					.newInstance();
			final Transformer transformer = transformerFactory.newTransformer();
			final DOMSource source = new DOMSource(base);

			final FileOutputStream os = new FileOutputStream(pomFile);

			try {
				final Result result = new StreamResult(os);
				transformer.transform(source, result);
			} finally {
				os.close();
			}
		}
	}

	/**
	 * Finds the best matching artifact from the list.
	 * 
	 * @param dependency
	 * @param jars
	 * @param depth
	 * @return null in case nothing was found, otherwise the correct systemPath
	 *         of the artifact
	 */
	private static String findArtifact(Dependency dependency,
			List<String> jars, int depth) {
		List<String> candidates = new ArrayList<String>();
		for (String jar : jars) {
			String fileName = jar.substring(jar.lastIndexOf('/') + 1);
			if (!fileName.startsWith(dependency.artifactId + "-"))
				continue;
			String localType = dependency.type == null ? "jar"
					: dependency.type;
			if (!fileName.endsWith("." + localType))
				continue;
			if (dependency.classifier != null) {
				if (fileName.endsWith("-" + dependency.classifier + "."
						+ localType))
					return toSystemPath(jar, depth);
			} else {
				candidates.add(jar);
			}
		}
		if (candidates.isEmpty())
			return null;
		if (candidates.size() == 1)
			return toSystemPath(candidates.get(0), depth);

		// this can be the case if there are multiple dependencies with
		// different classifiers.
		// we are looking for the one without classifier. It should have the
		// shortest length. Sorting by length would also have been an option ...
		int length = Integer.MAX_VALUE;
		int index = 0;
		for (int i = 0; i < candidates.size(); i++) {
			String jar = candidates.get(i);
			String fileName = jar.substring(jar.lastIndexOf('/') + 1);
			if (fileName.length() < length) {
				index = i;
				length = fileName.length();
			}
		}
		return toSystemPath(candidates.get(index), depth);

	}

	/**
	 * Converts the path from the war file into a full system path
	 * 
	 * @param jar
	 * @param depth
	 * @return
	 */
	private static String toSystemPath(String jar, int depth) {
		String points = "";
		for (int i = 0; i < depth; i++) {
			points += "/..";
		}

		return BASE_DIR + points + DEPENDENCY_PREFIX + jar;
	}

	private static List<String> readJars(String jarFile) throws IOException {
		List<String> jars = new ArrayList<String>();
		ZipFile zipFile = new ZipFile(jarFile);
		try {
			Enumeration<? extends ZipEntry> zipEntries = zipFile.entries();

			while (zipEntries.hasMoreElements()) {
				ZipEntry zipEntry = zipEntries.nextElement();
				if (zipEntry.isDirectory()) {
					continue;
				}
				if (!(zipEntry.getName().endsWith(".jar") || zipEntry.getName()
						.endsWith(".zip"))) {
					continue;
				}
				if (!zipEntry.getName().startsWith(MANDATORY_ZIP_ENTRY_PREFIX)) {
					continue;
				}

				jars.add(zipEntry.getName());
			}

			return jars;
		} finally {
			zipFile.close();
		}
	}

	private static class Dependency {
		String artifactId;
		String type;
		String classifier;

		@Override
		public String toString() {
			return "artifactId: " + artifactId + ", type: "
					+ (type == null ? "" : type) + ", classifier: "
					+ (classifier == null ? "" : classifier);
		}
	}

}
