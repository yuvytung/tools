#!/usr/bin/env groovy

node {
	stage('checkout')
	{
		checkout scm
	}

	stage('check java , node , docker , docker-compose')
	{
		sh "pwd"
		sh "java -version"
		sh "node -v"
		sh "npm -v"
		sh "docker -v"
		sh "docker-compose -v"
	}

	stage('clean')
	{
		sh "chmod +x gradlew"
		sh "./gradlew clean"
	}

	stage('build')
	{
		sh "./gradlew build"
	}

	stage ('check nexus 3 server' )
	{
		try
		{
			sh "docker container inspect nexus-3"
		}
		catch (err)
		{
			sh 'docker run -d -p 17010:8081 -v /root/volumes/nexus-3/nexus-data:/nexus-data -e INSTALL4J_ADD_VM_PARAMS="-Xms512m -Xmx1536m" --name nexus-3 sonatype/nexus3'
			sleep(30)
		}
	}


}

