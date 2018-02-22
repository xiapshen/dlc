#!/bin/sh
# 启动类
export MAIN_CLASS=com.happygo.dlc.api.DlcWebApplication

# start dlc-admin service
if [ "$1" = "start" ] ; then
    echo -------------------------------------------
    echo start dlc-web server
    echo -------------------------------------------

    # 设置项目代码路径
    export CODE_HOME=".."

    # 设置依赖路径
    export CLASSPATH="$CODE_HOME/WEB-INF/classes:$CODE_HOME/lib/*"

    # java可执行文件位置
    export _EXECJAVA="$JAVA_HOME/bin/java"

    # JVM启动参数
    export JAVA_OPTS="-server -Xms128m -Xmx256m -Xss256k -XX:MaxDirectMemorySize=128m"

    $_EXECJAVA $JAVA_OPTS -classpath $CLASSPATH $MAIN_CLASS &

# stop dlc-admin service
elif [ "$1" = "stop" ] ; then
    echo -------------------------------------------
    echo stop dlc-web server

    #所有相关进程
    PIDs=`jps -l | grep $MAIN_CLASS | awk '{print $1}'`
    #停止进程
    if [ -n "$PIDs" ]; then
      for PID in $PIDs; do
          kill $PID
          echo "kill $PID"
      done
    fi

    #等待50秒
    for i in 1 10; do
      PIDs=`jps -l | grep $MAIN_CLASS | awk '{print $1}'`
      if [ ! -n "$PIDs" ]; then
        echo "stop server success"
        echo -------------------------------------------
        break
      fi
      echo "sleep 5s"
      sleep 5
    done

    #如果等待50秒还没有停止完，直接杀掉
    PIDs=`jps -l | grep $MAIN_CLASS | awk '{print $1}'`
    if [ -n "$PIDs" ]; then
      for PID in $PIDs; do
          kill -9 $PID
          echo "kill -9 $PID"
      done
    fi
fi