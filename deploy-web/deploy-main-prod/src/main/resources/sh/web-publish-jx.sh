#!/bin/sh
GREEN="\e[0;32m" # 绿色
NC="\e[0m" # 没有颜色

DEPLOY_PROJECT_NAME=$1 #发布项目名称
DEPLOY_SOURCE_NAME=$2 #发布的源文件名称
DEPLOY_SOURCE_TYPE=$3 #发布的源文件类型
DEPLOY_TEMP_FOLDER=$4 #发布临时文件夹

if [[ ! -n ${DEPLOY_PROJECT_NAME} ]] ; then
	echo -e "${GREEN}#   请传入发布项目名称   #${NC}"
	exit -1
fi
if [[ ! -n ${DEPLOY_SOURCE_NAME} ]] ; then
	echo -e "${GREEN}#   发布的源文件名称   #${NC}"
	exit -1
fi
if [[ ! -n ${DEPLOY_SOURCE_TYPE} ]] ; then
	echo -e "${GREEN}#   发布的源文件类型   #${NC}"
	exit -1
fi
if [[ ! -n ${DEPLOY_TEMP_FOLDER} ]] ; then
	echo -e "${GREEN}#   发布临时文件夹   #${NC}"
	exit -1
fi

declare DEPLOY_WORKSPACE="/alidata/view/" #发布根目录
declare DEPLOY_SOURCE_WORKSPACE="/alidata/web-publish/history/" #发布源文件根目录

#项目编译后的文件夹路径
declare -A START_SOURCE_FOLDER_NAME_MAP=(
["jx-logistics"]="${DEPLOY_WORKSPACE}jx-logistics"
["screen"]="${DEPLOY_WORKSPACE}screen"
["jx-h5"]="${DEPLOY_WORKSPACE}jx-h5"
["jx-h5-home"]="${DEPLOY_WORKSPACE}jx-h5-home"
)

#发布逻辑开始
if [[ ! -n ${START_SOURCE_FOLDER_NAME_MAP[${DEPLOY_PROJECT_NAME}]} ]] ; then
    echo -e "${GREEN}[ ${DEPLOY_PROJECT_NAME} ] 发布项目不存在${NC}"
    exit -1
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
fi
if [[ "${DEPLOY_SOURCE_TYPE}" == "rar" ]] ; then
    rar x ${DEPLOY_SOURCE_NAME}
fi
#判断发布的文件是否存在
if [[ ! -d "${DEPLOY_SOURCE_WORKSPACE}${DEPLOY_TEMP_FOLDER}dist" ]];then
    echo -e "${GREEN}[ ${DEPLOY_PROJECT_NAME} ] 文件不存在 ${NC}"
    #删除临时文件
    rm -rf ${DEPLOY_SOURCE_WORKSPACE}${DEPLOY_TEMP_FOLDER}
	exit -1
fi
#删除老版本
rm -rf ${START_SOURCE_FOLDER_NAME_MAP[${DEPLOY_PROJECT_NAME}]}
cp -rf "${DEPLOY_SOURCE_WORKSPACE}${DEPLOY_TEMP_FOLDER}dist" ${START_SOURCE_FOLDER_NAME_MAP[${DEPLOY_PROJECT_NAME}]}
#删除临时文件
rm -rf ${DEPLOY_SOURCE_WORKSPACE}${DEPLOY_TEMP_FOLDER}
echo -en  "${GREEN}
#######################################################
#                      发布成功                       #
####################################################### ${NC}
"
exit 0