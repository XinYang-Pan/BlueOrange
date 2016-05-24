#!/bin/bash
#ident  "%W%"


APP_NAME=$1
BASEDIR=`dirname $0`

. ${BASEDIR}/env.source

RPMTMP=${TMPDIR}/rpm
PKGDIR=${RPMTMP}/pkg

rm -rf ${RPMTMP}
mkdir -p ${RPMTMP}
mkdir -p ${RPMTMP}/services
cd ${RPMTMP}

# copy app files to rpm tmp for packaging
cp -r ${APP_ROOT}/${APP_NAME} ${RPMTMP}/services/

# creating files for making rpm, like package.spec
pcreate_pkg ${RPMTMP} ${RPMTMP} ${APP_NAME} /opt 1.0 efp << EOF
n
n
EOF

# changing package.spec
sed -i "s/Vendor: Salomon Smith Barney Holdings Inc/Vendor: Citi/" ${PKGDIR}/package.spec
sed -i "s/Packager: Some Helpdesk number <some.email@citigroup.com>/Packager: xp09499@citi.com/" ${PKGDIR}/package.spec
sed -i "s/Release: APPID/Release: 1/" ${PKGDIR}/package.spec
sed -i "s/defattr(0444,root,root)/defattr(0755,rtictl,slprod)/" ${PKGDIR}/package.spec

# making the rpm
make package --directory ${PKGDIR}

# copy rpm to rpm dir
cp ${RPMTMP}/${APP_NAME}*.rpm ${RPMDIR}

rm -rf ${RPMTMP}
