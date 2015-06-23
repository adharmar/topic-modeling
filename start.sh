mkdir $1
../../bin/hadoop dfs -mkdir /$1
../../bin/hadoop dfs -copyFromLocal hinput.txt /$1
javac -classpath ../../hadoop-0.20.2-core.jar -d $1 HTopicModel.java LdaGibbsSampler.java DocTopicDistGenerator.java WordGen.java Naive.java NaiveMerger.java StopWordRemoval.java SA2S.java
jar -cvf /usr/local/hadoop/Project\ Hadoop/src/$1.jar -C $1/ .
../../bin/hadoop jar /usr/local/hadoop/Project\ Hadoop/src/$1.jar org.myorg.proj.HTopicModel /$1/hinput.txt /$1/op.txt
../../bin/hadoop dfs -copyToLocal /$1/op.txt $2
