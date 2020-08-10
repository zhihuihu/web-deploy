#!/bin/sh
GREEN="\e[0;32m" # 绿色
NC="\e[0m" # 没有颜色

DEPLOY_PROJECT_NAME=$1 #发布项目名称
DEPLOY_SOURCE_NAME=$2 #发布的源文件名称
DEPLOY_SOURCE_TYPE=$3 #发布的源文件类型
DEPLOY_TEMP_FOLDER=$4 #发布临时文件夹
DEPLOY_WORKSPACE=$5 #发布根目录
DEPLOY_SOURCE_WORKSPACE=$6 #发布源文件根目录
DEPLOY_CHECK_FOLDER=$7 #发布检查文件夹

if [[ ! -n ${DEPLOY_PROJECT_NAME} ]] ; then
	echo -e "${GREEN}#   请传入发布项目名称   #${NC}"
	exit 1
fi
if [[ ! -n ${DEPLOY_SOURCE_NAME} ]] ; then
	echo -e "${GREEN}#   请传入发布的源文件名称   #${NC}"
	exit 1
fi
if [[ ! -n ${DEPLOY_SOURCE_TYPE} ]] ; then
	echo -e "${GREEN}#   请传入发布的源文件类型   #${NC}"
	exit 1
fi
if [[ ! -n ${DEPLOY_TEMP_FOLDER} ]] ; then
	echo -e "${GREEN}#   请传入发布临时文件夹   #${NC}"
	exit 1
fi
if [[ ! -n ${DEPLOY_WORKSPACE} ]] ; then
	echo -e "${GREEN}#   请传入发布根目录   #${NC}"
	exit 1
fi
if [[ ! -n ${DEPLOY_SOURCE_WORKSPACE} ]] ; then
	echo -e "${GREEN}#   请传入发布源文件根目录   #${NC}"
	exit 1
fi
if [[ ! -n ${DEPLOY_CHECK_FOLDER} ]] ; then
	echo -e "${GREEN}#   请传入发布检查文件夹   #${NC}"
	exit 1
fi

#删除临时文件
rm -rf ${DEPLOY_SOURCE_WORKSPACE}${DEPLOY_TEMP_FOLDER}

#切换工作目录至临时文件夹
cd ${DEPLOY_SOURCE_WORKSPACE}
mkdir ${DEPLOY_TEMP_FOLDER}
cd ${DEPLOY_SOURCE_WORKSPACE}${DEPLOY_TEMP_FOLDER}
#复制源文件至临时文件夹
cp -rf ${DEPLOY_SOURCE_WORKSPACE}${DEPLOY_SOURCE_NAME} ${DEPLOY_SOURCE_WORKSPACE}${DEPLOY_TEMP_FOLDER}
#解压文件
if [[ "${DEPLOY_SOURCE_TYPE}" == "zip" ]] ; then
    unzip ${DEPLOY_SOURCE_NAME}
    rc=$?
    if [[ $rc -ne 1 ]] ; then
      echo -en  "${GREEN}[ ${DEPLOY_PROJECT_NAME} ]
      #######################################################
      #                      zip解压失败                        #
      ####################################################### ${NC}
      "
      rm -rf ${DEPLOY_SOURCE_WORKSPACE}${DEPLOY_TEMP_FOLDER}
      rm -rf ${DEPLOY_SOURCE_WORKSPACE}${DEPLOY_SOURCE_NAME}
      cd -
      exit $rc
    fi
fi
if [[ "${DEPLOY_SOURCE_TYPE}" == "rar" ]] ; then
    rar x ${DEPLOY_SOURCE_NAME}
    rc=$?
    if [[ $rc -ne 0 ]] ; then
      echo -en  "${GREEN}[ ${DEPLOY_PROJECT_NAME} ]
      #######################################################
      #                      rar解压失败                        #
      ####################################################### ${NC}
      "
      cd -
      rm -rf ${DEPLOY_SOURCE_WORKSPACE}${DEPLOY_TEMP_FOLDER}
      rm -rf ${DEPLOY_SOURCE_WORKSPACE}${DEPLOY_SOURCE_NAME}
      exit $rc
    fi
fi
#判断发布的文件是否存在
if [[ ! -d "${DEPLOY_SOURCE_WORKSPACE}${DEPLOY_TEMP_FOLDER}${DEPLOY_CHECK_FOLDER}" ]];then
    echo -e "${GREEN}[ ${DEPLOY_PROJECT_NAME} ] 文件不存在 ${NC}"
    #删除临时文件
    rm -rf ${DEPLOY_SOURCE_WORKSPACE}${DEPLOY_TEMP_FOLDER}
    rm -rf ${DEPLOY_SOURCE_WORKSPACE}${DEPLOY_SOURCE_NAME}
	exit 1
fi
#删除老版本
rm -rf ${DEPLOY_WORKSPACE}${DEPLOY_PROJECT_NAME}
cp -rf "${DEPLOY_SOURCE_WORKSPACE}${DEPLOY_TEMP_FOLDER}${DEPLOY_CHECK_FOLDER}" ${DEPLOY_WORKSPACE}${DEPLOY_PROJECT_NAME}
#删除临时文件
rm -rf ${DEPLOY_SOURCE_WORKSPACE}${DEPLOY_TEMP_FOLDER}
rm -rf ${DEPLOY_SOURCE_WORKSPACE}${DEPLOY_SOURCE_NAME}
echo -en  "${GREEN}
#######################################################
#                      发布成功                       #
####################################################### ${NC}
"
exit 0
