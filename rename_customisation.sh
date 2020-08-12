#!/bin/sh

# Changes the package name of the customisation template

set -e

usage() {
 cat << EOF
Usage: rename_customisation.sh <package>

rename_customisation.sh can be used to change the package name of the customisation.
The package name should have at least three parts.

Examples
    ./rename_customisation.sh com.github.sap.mobiliser.template

EOF
}

# shellcheck disable=SC2159
while [ 0 ]; do
  if [ "x$1" = "x-h" ] || [ "x$1" = "x--help" ]; then
    usage;
    exit 0
  else
    break
  fi
done

package="$1"

if [ "x$package" = "x" ] ; then
  echo >&2 "Package is required"
  usage;
  exit 1
fi

parts="$(echo "$package" | tr -c -d "." | wc -m)"
parts=$((parts+1))

if [ $parts -lt 3 ] ; then
  echo >&2 "Package name should have at least three parts"
  usage;
  exit 2
fi

directory="$(echo "$package" | sed -e 's/\./\//g')"
directory2="$(echo "$package" | sed -e 's/\./\//g' | rev | cut -d/ -f2- | rev)"

# rename groupId
find . -name '*.xml' \! -path '*/target/*' -print0 | xargs -0 -I {} perl -i -pe "s/com\.sap\.mobile\.platform\.server\.appservices\.money\.customization/$package.customisation/g" "{}"

# rename artefactIds
find . \( -name '*.java' -o -name '*.xml' -o -name '*.xsd' \) \! -path '*/target/*' -print0 | xargs -0 -I {} perl -i -pe "s/com\.sybase365\.mobiliser\.custom\.project/$package/g" "{}"

# move sources
# shouldn't need to assign {} to xx here but otherwise it doesn't work on OS X
find . -depth -name 'project' -type d \! -path '*/target/*' -print0 | xargs -0 -I {} sh -c 'xx="{}" ; root="$(dirname "$(dirname "$(dirname "$(dirname "$(dirname "$xx")")")")")"; mkdir -p "$root/'"$directory2"'" && mv "$xx/" "$root/'"$directory"'"'

# update context.properties
lowerdomain="$(echo "$package" | cut -d. -f2)"
# shellcheck disable=SC2018,SC2019
domain="$(echo "$lowerdomain" | cut -c1 | tr 'a-z' 'A-Z')$(echo "$lowerdomain" | cut -c2-)"
project="$(echo "$package" | rev | cut -d. -f1 | rev)"
# shellcheck disable=SC2018,SC2019
upperProject="$(echo "$project" | cut -c1 | tr 'a-z' 'A-Z')$(echo "$project" | cut -c2-)"

find . -name 'context.properties' \! -path '*/target/*' -print0 | xargs -0 -I {} perl -i -pe "s#contextPath=custom#contextPath=$project#;s#wsdlName=Custom#wsdlName=$upperProject#;s#portTypeName=CustomSoapPort#portTypeName=$upperProject""SoapPort#;s#serviceName=CustomService#serviceName=$upperProject""Service#;s#jsClientName=customService#jsClientName=$project""Service#" "{}"

# update jmeter test project URLs
find . -name '*jmx' \! -path '*/target/*' -print0 | xargs -0 -I {} perl -i -pe "s#mobiliser/rest/custom/#mobiliser/rest/$project/#g" "{}"

# fix paths in security adviser configs
find . -name '*.xml' \! -path '*/target/*' -print0 | xargs -0 -I {} perl -i -pe "s#path=/custom#path=/$project#g;s#value=\"/custom\"#value=\"/$project\"#g" "{}"


# fix xsd references
# ../../../../../framework
# ../../../../../money

# count depth
dircount="$(echo "$package" | tr -c -d "." | wc -m)"
# add current depth
dircount=$((dircount+4))

# generate path
# shellcheck disable=SC2034
pathstart="$(for i in $(seq 1 $dircount) ; do printf "../" ; done)"

find . -name '*.xsd' \! -path '*/target/*' -print0 | xargs -0 -I{} perl -i -pe "s#../../../../../#""$pathstart""com/sybase365/mobiliser/#" "{}"

# replace http://mobiliser.sybase365.com/custom/project/ in xsds
tmp="$(echo "$package" | cut -d. -f-3)"
part1="$(echo "$tmp" | cut -d. -f3).$(echo "$tmp" | cut -d. -f2).$(echo "$tmp" | cut -d. -f1)"
part2="$(echo "$package" | cut -d. -f4- | sed -e 's/\./\//g')"

find . -name '*.xsd' \! -path '*/target/*' -print0 | xargs -0 -I {} perl -i -pe "s#http://mobiliser\.sybase365\.com/custom/project/#http://$part1/$part2/#g" "{}"

# fix xsd imports in bundle-contex.xmls
find . -name '*.xml' \! -path '*/target/*' -print0 | xargs -0 -I {} perl -i -pe "s#/com/sybase365/mobiliser/custom/project/#/$directory/#g" "{}"

# fix namespace in WSDLs
find . -name 'context.properties' \! -path '*/target/*' -print0 | xargs -0 -I {} perl -i -pe "s#namespace=http\\\\://mobiliser\.sybase365\.com/custom/project#namespace=http\\\\://$part1/$part2/#" "{}"

# fix <name> in pom.xml
find . -name 'pom.xml' \! -path '*/target/*' -print0 | xargs -0 -I {} perl -i -pe "s#AIMS Mobiliser :: Custom :: Project#AIMS Mobiliser :: $domain :: $upperProject#" "{}"

