package com.example.board.controller;

import com.example.board.service.AutoService;
import com.example.board.vo.request.ImgAugRequestVo;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.util.Iterator;

@Controller
@AllArgsConstructor
public class AutoController {
    private AutoService autoService;

    @RequestMapping("/script")
    public String getScript(){
        return "#!/bin/bash\n" +
                "#ARG=($@)\n" +
                "\n" +
                "WORK_HOME=\"/home1/irteam\"\n" +
                "APP_HOME=\"${WORK_HOME}/apps\"\n" +
                "DIRS=\"apps deploy logs logs/apache logs/tomcat scripts\"\n" +
                "TIMEIDX=$(date +%y%m%d%H%M%S)\n" +
                "REPO_ADDR=\"repo.nhnsystem.com\";\n" +
                "\n" +
                "\n" +
                "#Print message & Exit\n" +
                "msgexit() {\n" +
                "        echo $1\n" +
                "        exit 1\n" +
                "}\n" +
                "\n" +
                "\n" +
                "help() {\n" +
                "        echo \"Usage: $0 [OPTIONS]\"\n" +
                "        echo \"Options:\"\n" +
                "        echo \"  -h, --help     print this messages\"\n" +
                "        echo \"  -v, --version     print available version\"\n" +
                "        echo \"  -a {version}, --apache {version}    set apache version\"\n" +
                "        echo \"  -t {version}, --tomcat {version}    set tomcat version\"\n" +
                "        echo \"  -j {version}, --jdk {version}    set jdk version\"\n" +
                "        echo \"  -m {naver|nbp}, --module {naver|nbp}    install auth module\"\n" +
                "        exit 1\n" +
                "}\n" +
                "\n" +
                "\n" +
                "version() {\n" +
                "        curl -s http://$REPO_ADDR/webapps/VERSIONS.TXT\n" +
                "}\n" +
                "\n" +
                "\n" +
                "# Check Version\n" +
                "check_version() {\n" +
                "\n" +
                "        VER_TXT=$(curl -s http://$REPO_ADDR/webapps/VERSION.TXT|sed 's/\\s//g;/^$/d')\n" +
                "\n" +
                "        cd $WORK_HOME\n" +
                "        wget -q http://$REPO_ADDR/webapps/README.WEBAPPS -O README.WEBAPPS\n" +
                "\n" +
                "        for i in $VER_TXT\n" +
                "        do\n" +
                "                temp=${i/=*/}\n" +
                "                if [ -z ${!temp} ];then\n" +
                "                        # Assignment BUILD,SCRIPT,APACHE,TOMCAT,JDK\n" +
                "                        eval $i\n" +
                "                fi\n" +
                "        done\n" +
                "\n" +
                "        JDK_VER_MAJOR=`echo $JDK | cut -d'.' -f1`\n" +
                "        catalina_opt='#CATALINA_OPTS=\"-server -Xms1024m -Xmx1024m -XX:MaxPermSize=128m -XX:+UseParNewGC -XX:+UseConcMarkSweepGC -XX:+CMSClassUnloadingEnabled -verbose:gc -XX:+PrintGCDetails -XX:+PrintGCTimeStamps -Xloggc:/home1/irteam/logs/gc.log -XX:+UseGCLogFileRotation -XX:NumberOfGCLogFiles=10 -XX:GCLogFileSize=2M -XX:+HeapDumpOnOutOfMemoryError -XX:HeapDumpPath=/home1/irteam/logs/heap-was1.log\"'\n" +
                "\n" +
                "        ## ÀÓ½Ã Tomcat6 / jdk6 ¼³Ä¡ Áö¿ø ¸ñÀû\n" +
                "        if [[ ${JDK} =~ ^1\\.8 ]];then\n" +
                "                catalina_opt='#CATALINA_OPTS=\"-server -Xms1024m -Xmx1024m -XX:+UseG1GC -verbose:gc -XX:+PrintGCDetails -XX:+PrintGCTimeStamps -Xloggc:/home1/irteam/logs/gc.log -XX:+UseGCLogFileRotation -XX:NumberOfGCLogFiles=10 -XX:GCLogFileSize=2M -XX:+HeapDumpOnOutOfMemoryError -XX:HeapDumpPath=/home1/irteam/logs/heap-was1.hprof\"'\n" +
                "        elif [ $JDK_VER_MAJOR -ge 10 ];then\n" +
                "                catalina_opt='#CATALINA_OPTS=\"-server -Xms1024m -Xmx1024m -XX:+UseG1GC -verbose:gc -XX:+HeapDumpOnOutOfMemoryError -XX:HeapDumpPath=/home1/irteam/logs/heap-was1.hprof\"'\n" +
                "        fi\n" +
                "        TOMCAT_TAR=\"tomcat-${TOMCAT}.tar.gz\"\n" +
                "\n" +
                "        D_APACHE=\"apache-${APACHE}\"\n" +
                "        D_TOMCAT=\"apache-tomcat-${TOMCAT}\"\n" +
                "\n" +
                "        [ $JDK_VER_MAJOR -lt 10 ] &&  D_JDK=\"jdk${JDK}\" || D_JDK=\"jdk-${JDK}\"\n" +
                "\n" +
                "        echo \"--------------------\"\n" +
                "        echo \"1. Build Information\"\n" +
                "        echo \" - Build: $BUILD\"\n" +
                "        echo \" - Apache: $D_APACHE\"\n" +
                "        echo \" - Tomcat: $D_TOMCAT\"\n" +
                "        echo \" - JDK: $D_JDK\"\n" +
                "        echo \"--------------------\"\n" +
                "}\n" +
                "\n" +
                "# Check existence\n" +
                "check_pre() {\n" +
                "        if [ -d $APP_HOME ];then\n" +
                "                cd $APP_HOME\n" +
                "\n" +
                "                for i in $D_TOMCAT tomcat\n" +
                "                do\n" +
                "                        if [ -d \"$i\" -o -h \"$i\" ];then\n" +
                "                                msgexit \"$APP_HOME/$i is already exist\"\n" +
                "                        fi\n" +
                "                done\n" +
                "        fi\n" +
                "        echo \"--------------------\"\n" +
                "        echo \"2. Installtaion\"\n" +
                "}\n" +
                "\n" +
                "# OS version check\n" +
                "check_os() {\n" +
                "        OS_VERSION=$(sed -r 's/.*release ([0-9]).*/\\1/' /etc/redhat-release)\n" +
                "        OS_ARCH=$(uname -i)\n" +
                "}\n" +
                "\n" +
                "# User check\n" +
                "check_user() {\n" +
                "        #UID=$(id -u)\n" +
                "\n" +
                "        if [ \"$UID\"  !=  \"0\" ];then\n" +
                "                echo \"UID:$UID => This has to be ran by root(or sudo)\"\n" +
                "                exit 1\n" +
                "        fi\n" +
                "}\n" +
                "\n" +
                "# Setting directory\n" +
                "set_dir() {\n" +
                "        cd $WORK_HOME\n" +
                "\n" +
                "        for i in $DIRS\n" +
                "        do\n" +
                "                if [ ! -d $i ];then\n" +
                "                        mkdir -p $i\n" +
                "                        chown -R irteam.irteam $i\n" +
                "                fi\n" +
                "        done\n" +
                "}\n" +
                "\n" +
                "# User bash enviornment\n" +
                "\n" +
                "set_bashenv(){\n" +
                "\n" +
                "        BASH_ENV='\\t### WEBAPPS ENV ###\n" +
                "        export APP_HOME=/home1/irteam\n" +
                "        export JAVA_HOME=${APP_HOME}/apps/jdk\n" +
                "        export APACHE_HTTP_HOME=${APP_HOME}/apps/apache\n" +
                "        export TOMCAT_HOME=${APP_HOME}/apps/tomcat\n" +
                "        export PATH=${JAVA_HOME}/bin:$PATH\n" +
                "        export PATH=${APACHE_HTTP_HOME}/bin:$PATH'\n" +
                "\n" +
                "        JHOME=$(su -s /bin/bash - irteam -c 'echo $JAVA_HOME')\n" +
                "        if [ \"$?\" -eq 0 ]  &&  [ \"${JHOME}\" == \"\" ]; then\n" +
                "                echo -e \"$BASH_ENV\" >> ${WORK_HOME}/.bashrc\n" +
                "        else\n" +
                "                echo -e \"\\n### Set Shell Environment please ###\\n$BASH_ENV\"\n" +
                "        fi\n" +
                "}\n" +
                "\n" +
                "# Apache setting\n" +
                "set_apache(){\n" +
                "        cd $APP_HOME\n" +
                "        if [ \"$OS_ARCH\" == \"x86_64\" ];then\n" +
                "                APACHE_TAR=\"apache-${APACHE}-c${OS_VERSION}-x64.tar.gz\";\n" +
                "                nauth_arch=\"64\";\n" +
                "        else\n" +
                "                APACHE_TAR=\"apache-${APACHE}-c${OS_VERSION}-i386.tar.gz\";\n" +
                "                nauth_arch=\"86\";\n" +
                "        fi\n" +
                "\n" +
                "        echo -n \" - Apache Installation =>\"\n" +
                "        if [ ${OS_VERSION} -ge 7 ] && [[ \"${APACHE}\" =~ ^2.4. ]];then\n" +
                "                if [ `rpm -q nghttp2 lignghttp2|grep -v 'not installed' -c` -ne 2 ];then\n" +
                "                        yum -y install nghttp2 libnghttp2\n" +
                "                fi\n" +
                "        fi\n" +
                "\n" +
                "        wget -q http://$REPO_ADDR/webapps/application/apache/${APACHE_TAR} -O ${APACHE_TAR}\n" +
                "        tar -zxf ${APACHE_TAR}\n" +
                "        rm -f ${APACHE_TAR}\n" +
                "\n" +
                "        (test -h apache) || (ln -s apache-${APACHE} apache)\n" +
                "        chown -R irteam.irteam apache-${APACHE}\n" +
                "        chown -h irteam.irteam apache\n" +
                "        if [ ${OS_VERSION} -ge 6 ];then\n" +
                "                chown irteam.irteam apache-${APACHE}/bin/httpd\n" +
                "                /usr/sbin/setcap 'cap_net_bind_service=+ep' apache-${APACHE}/bin/httpd\n" +
                "        else\n" +
                "                chown root.irteam apache-${APACHE}/bin/httpd\n" +
                "                chmod 4750 apache-${APACHE}/bin/httpd\n" +
                "        fi\n" +
                "\n" +
                "        echo \"OK!!\"\n" +
                "}\n" +
                "\n" +
                "\n" +
                "# Tomcat setting\n" +
                "set_tomcat(){\n" +
                "        cd $APP_HOME\n" +
                "\n" +
                "        echo -n \" - Tomcat Installtaion =>\"\n" +
                "\n" +
                "        wget -q http://$REPO_ADDR/webapps/application/tomcat/${TOMCAT_TAR} -O ${TOMCAT_TAR}\n" +
                "        tar -zxf ${TOMCAT_TAR}\n" +
                "        rm -f ${TOMCAT_TAR}\n" +
                "        ( test -h tomcat ) || (ln -s apache-tomcat-${TOMCAT} tomcat)\n" +
                "        chown -h irteam.irteam tomcat\n" +
                "        echo \"OK!!\"\n" +
                "}\n" +
                "\n" +
                "# JDK setting\n" +
                "set_jdk(){\n" +
                "        cd $APP_HOME\n" +
                "\n" +
                "        JDK_VER_MAJOR=`echo $JDK | cut -d'.' -f1`\n" +
                "\n" +
                "        if [ $JDK_VER_MAJOR -lt 10 ];then\n" +
                "                JDK_VER=`echo $JDK|sed 's/^[0-9]\\+\\.//;s/\\.0_/u/'`\n" +
                "                if [ \"$OS_ARCH\" == \"x86_64\" ];then\n" +
                "                        JDK_BIN=\"jdk-${JDK_VER}-linux-x64.bin\"\n" +
                "                        JDK_TAR=\"jdk-${JDK_VER}-linux-x64.tar.gz\"\n" +
                "                else\n" +
                "                        JDK_BIN=\"jdk-${JDK_VER}-linux-i586.bin\"\n" +
                "                        JDK_TAR=\"jdk-${JDK_VER}-linux-i586.tar.gz\"\n" +
                "                fi\n" +
                "        else\n" +
                "                JDK_BIN=\"\" # not used\n" +
                "                JDK_VER=$JDK\n" +
                "                if [ \"$OS_ARCH\" == \"x86_64\" ];then\n" +
                "                        [ $JDK_VER_MAJOR -ge 11 ] && OPEN=\"open\" || OPEN=\"\"\n" +
                "                        JDK_TAR=\"${OPEN}jdk-${JDK_VER}_linux-x64_bin.tar.gz\"\n" +
                "                else\n" +
                "                        # JAVA does not support\n" +
                "                        msgexit \"only support x86_64 for JDK version over 9\"\n" +
                "                fi\n" +
                "        fi\n" +
                "\n" +
                "        echo -n \" - JDK Installtaion =>\"\n" +
                "\n" +
                "        if [[ ${JDK_VER} =~ ^6u.* ]]; then\n" +
                "\n" +
                "                wget -q http://$REPO_ADDR/webapps/application/jdk/$JDK_BIN -O $JDK_BIN\n" +
                "                chmod 755 $JDK_BIN\n" +
                "                ./$JDK_BIN -noregister > /dev/null\n" +
                "                rm -f $JDK_BIN\n" +
                "        else\n" +
                "                wget -q http://$REPO_ADDR/webapps/application/jdk/$JDK_TAR -O $JDK_TAR\n" +
                "                tar xfz $JDK_TAR\n" +
                "                rm -f $JDK_TAR\n" +
                "        fi\n" +
                "\n" +
                "        ( test -h jdk ) || (ln -s $D_JDK jdk)\n" +
                "        chown -h irteam.irteam jdk\n" +
                "        echo \"OK!!\"\n" +
                "}\n" +
                "\n" +
                "# Cronolog for Tomcat Setting\n" +
                "set_cronolog(){\n" +
                "        cd $WORK_HOME\n" +
                "        ( test -f $APP_HOME/cronolog/sbin/cronolog ) &&  msgexit \"Cronolog is already exist\"\n" +
                "        ( test -d src ) || (mkdir src)\n" +
                "\n" +
                "        cd src\n" +
                "        echo -n \" - Cronolog Installation => \"\n" +
                "        wget -q http://$REPO_ADDR/webapps/application/tomcat/cronolog-1.6.2.tar.gz -O cronolog-1.6.2.tar.gz\n" +
                "        tar -zxf cronolog-1.6.2.tar.gz >/dev/null\n" +
                "\n" +
                "        cd cronolog-1.6.2\n" +
                "        ./configure --prefix=$APP_HOME/cronolog-1.6.2 >/dev/null\n" +
                "        (make > /dev/null && make install > /dev/null) &> ../cronolog.err\n" +
                "\n" +
                "        cd $APP_HOME\n" +
                "        chown -R irteam.irteam  cronolog-1.6.2\n" +
                "        ( test -h cronolog ) || (ln -s cronolog-1.6.2 cronolog)\n" +
                "        chown -h irteam.irteam cronolog\n" +
                "        chown -R irteam.irteam $WORK_HOME/src\n" +
                "\n" +
                "        echo \"OK!! (errror log: $APP_HOME/src/cronolog.err)\"\n" +
                "}\n" +
                "\n" +
                "# sync files\n" +
                "sync_common(){\n" +
                "        echo -n \" - Sync files => \"\n" +
                "        # rsync #1\n" +
                "        rsync -a --exclude=.svn --exclude=apps $REPO_ADDR::webapps/ $WORK_HOME/\n" +
                "        rsync -a --exclude=.svn --exclude=modules $REPO_ADDR::webapps/apps/apache-${APACHE}/ $WORK_HOME/apps/apache-${APACHE}/\n" +
                "        rsync -a --exclude=.svn $REPO_ADDR::webapps/apps/apache-tomcat-${TOMCAT}/ $WORK_HOME/apps/apache-tomcat-${TOMCAT}/\n" +
                "        rsync -a --exclude=.svn $REPO_ADDR::webapps/apps/$D_JDK/ $WORK_HOME/apps/$D_JDK/\n" +
                "\n" +
                "        if [ \"${NAUTH}\" != \"\" ];then\n" +
                "\n" +
                "                rsync -a -L \\\n" +
                "                        $REPO_ADDR::webapps/apps/apache-${APACHE}/modules/${OS_ARCH}/mod_${NAUTH}-latest_apache-${APACHE%.*}_CentOS-${OS_VERSION}.x_x${nauth_arch}.so \\\n" +
                "                        $WORK_HOME/apps/apache-${APACHE}/modules/\n" +
                "                chmod 755 $WORK_HOME/apps/apache-${APACHE}/modules/mod_${NAUTH}-latest_apache-${APACHE%.*}_CentOS-${OS_VERSION}.x_x${nauth_arch}.so\n" +
                "                ln -s mod_${NAUTH}-latest_apache-${APACHE%.*}_CentOS-${OS_VERSION}.x_x${nauth_arch}.so $WORK_HOME/apps/apache-${APACHE}/modules/mod_nvauth.so\n" +
                "                chown -h irteam.irteam $WORK_HOME/apps/apache-${APACHE}/modules/mod_nvauth.so\n" +
                "                sed -i '/mod_nvauth.so/s/^#//' $WORK_HOME/apps/apache-${APACHE}/conf/httpd.conf\n" +
                "\n" +
                "        fi\n" +
                "        chown -R irteam.irteam $WORK_HOME/apps/apache-${APACHE}/conf $WORK_HOME/apps/apache-${APACHE}/modules\n" +
                "        chown -R irteam.irteam $WORK_HOME/apps/apache-tomcat-${TOMCAT}\n" +
                "        chown -R irteam.irteam $WORK_HOME/apps/$D_JDK\n" +
                "        chown -R irteam.irteam $WORK_HOME/scripts\n" +
                "\n" +
                "        echo ${catalina_opt} >> $WORK_HOME/apps/apache-tomcat-${TOMCAT}/bin/setenv.sh\n" +
                "        echo \"OK!!\"\n" +
                "        echo \"--------------------\"\n" +
                "\n" +
                "        # 위의 rsync #1 에 의해서 /home1/irteam 의 소유권과 퍼미션이 변경이 된다.\n" +
                "        # irteam -> root, 750 -> 755\n" +
                "        # 일단 소유권만 복구 시킨다.\n" +
                "        chown irteam.irteam $WORK_HOME\n" +
                "        chown -h irteam.irteam $WORK_HOME/apps/*\n" +
                "        chown -R irteam.irteam $WORK_HOME/deploy/\n" +
                "}\n" +
                "\n" +
                "### main ###\n" +
                "OPTS=$(getopt -o hva:t:j:m: -l help,version,apache:,tomcat:,jdk:,module: -- $*)\n" +
                "set -- ${OPTS}\n" +
                "\n" +
                "while true; do\n" +
                "        case \"$1\" in\n" +
                "                -h|--help)\n" +
                "                        help\n" +
                "                        break;\n" +
                "                        ;;\n" +
                "                -v|--version)\n" +
                "                        version\n" +
                "                        exit 1;\n" +
                "                        ;;\n" +
                "                -a|--apache)\n" +
                "                        APACHE=${2//\\'/}\n" +
                "                        if ! $(version|grep '^apache'|grep -qw ${2//\\'/}) ;then\n" +
                "                                version\n" +
                "                                msgexit \"No usable version $2\"\n" +
                "                        fi\n" +
                "                        shift 2;\n" +
                "                        ;;\n" +
                "                -t|--tomcat)\n" +
                "                        TOMCAT=${2//\\'/}\n" +
                "                        if ! $(version|grep '^tomcat'|grep -qw ${2//\\'/}) ;then\n" +
                "                                version\n" +
                "                                msgexit \"No usable version $2\"\n" +
                "                        fi\n" +
                "                        shift 2;\n" +
                "                        ;;\n" +
                "                -j|--jdk)\n" +
                "                        JDK=${2//\\'/}\n" +
                "                        if ! $(version|grep '^jdk'|grep -qw ${2//\\'/}) ;then\n" +
                "                                version\n" +
                "                                msgexit \"No usable version $2\"\n" +
                "                        fi\n" +
                "                        shift 2;\n" +
                "                        ;;\n" +
                "                -m|--module)\n" +
                "                        MODULE=${2//\\'/}\n" +
                "                        if [ $MODULE == \"naver\" ];then\n" +
                "                                NAUTH=\"nvauth\"\n" +
                "                        elif  [ $MODULE == \"nbp\" ];then\n" +
                "                                NAUTH=\"nvextauth\"\n" +
                "                        else\n" +
                "                                msgexit \"Not exist module $2\"\n" +
                "                        fi\n" +
                "                        shift 2;\n" +
                "                        ;;\n" +
                "                --)\n" +
                "                        break;\n" +
                "        esac\n" +
                "done\n" +
                "\n" +
                "### Pre Checking ###\n" +
                "#check_options\n" +
                "#check_idc\n" +
                "check_version\n" +
                "check_os\n" +
                "check_user\n" +
                "check_pre\n" +
                "##check_update\n" +
                "\n" +
                "### setting apache/tomcat/jdk ###\n" +
                "set_dir\n" +
                "#set_apache\n" +
                "set_tomcat\n" +
                "#set_jdk\n" +
                "#set_cronolog\n" +
                "sync_common\n" +
                "set_bashenv\n" +
                "\n" +
                "echo \"Completed!!\"\n" +
                "\n" +
                "# Embeded VIM Configurations\n" +
                "# vim: filetype=sh si smarttab noet sw=4 ts=4 fdm=marker\n" +
                "\n";
    }

    // 이미지 upload 기본 html
    @RequestMapping("/test")
    public String test() {
        // 사용자에게 현재 폴더에 있는 이미지 목록 제공
        String result = autoService.getFileList();
        System.out.println(result);
        return "/project/home.html";
    }

    // Java에서 cmd 실행 test
    @RequestMapping("/terminal")
    @ResponseBody
    public String terminalTest() { // 추후에는 request에 명령어를 담아서 오자.
        String[] cmd = {"ifconfig"};
        String result = autoService.execCommand(cmd);
        return result;
    }

    // 이미지 upload test
    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    @ResponseBody
    public String fileUpload(MultipartHttpServletRequest request) {
        return autoService.fileUpload(request);
    }

    // image augmentation 요청
    @RequestMapping(value = "/imgAug", method = RequestMethod.POST)
    @ResponseBody
    public String imageAugmentation(MultipartHttpServletRequest request) { // 추후에 @Requestbody?? ImgAugRequestVo request로 수정해야하는데 FE에서 어떻게 전달해야하는지 모르겠네
        return autoService.imgAug(request);
    }
}
