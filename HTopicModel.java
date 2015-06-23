
package org.myorg.proj;
import java.io.*;
import java.util.*;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.conf.*;
import org.apache.hadoop.io.*;
import org.apache.hadoop.mapred.*;
import org.apache.hadoop.util.*;

public class HTopicModel {

	public static class LdaMapper extends MapReduceBase implements Mapper<LongWritable, Text, LongWritable, Text>
	{
		public void map(LongWritable key, Text value, OutputCollector<LongWritable, Text> output, Reporter reporter) throws IOException
		{
			//String docdir = key.toString();
			LdaGibbsSampler lgs=new LdaGibbsSampler();

			/*String value to 2D Array*/
			String values=value.toString();
			System.out.println(values);
			String[] temp=values.split("#");
			int[][] docs=new int[temp.length][];
			for(int i=0;i<temp.length;i++)
			{
				String[] temp2=temp[i].split("@");
				docs[i]=new int[temp2.length];
				for(int j=0;j<temp2.length;j++)
				{
					docs[i][j]=Integer.parseInt(temp2[j]);
				}
			}


			//int[][] docs=value.toArray();
			String docdir="docdir_temp/";
			float[][] phi=lgs.lda(docdir,docs);
			String finalstr="";
			for(int i=0;i<phi.length;i++)
			{
				for(int j=0;j<phi[i].length;j++)
				{
					finalstr=finalstr+phi[i][j]+"@";
				}
				finalstr=finalstr+"#";
				//System.out.println("The key-value is :"+key+ " and "+finalstr);
			}
			FileWriter fw=new FileWriter("/usr/local/hadoop/intermediate.txt",true);
			BufferedWriter fwout=new BufferedWriter(fw);
			fwout.write(finalstr);
			fwout.close();

			output.collect(key, new Text(finalstr));
		}
	}

public static class LdaReducer extends MapReduceBase implements Reducer<LongWritable, Text, LongWritable, Text>
{
	public void reduce(LongWritable key, Iterator<Text> value, OutputCollector<LongWritable, Text> output, Reporter reporter) throws IOException
	{
		NaiveMerger nm=new NaiveMerger();
		Naive nv=new Naive();
		DocTopicDistGenerator dtdg=new DocTopicDistGenerator();
		Text x=new Text();
		Text x2=new Text();
		SA2S sarr2s=new SA2S();

		//Iterator<Text> value2=value;
		//value2=value;
		//String[] z;

		//while(value.hasNext())
		//{
			x.set(value.next());
		//}
		String y=x.toString();
		String[] z=nv.fn(y,y);
/*new include*/
		String temp=sarr2s.func(z);

		//value.reset();
		while(value.hasNext())
		{
			x2.set(value.next());
			String p=x2.toString();
			z=nv.fn(p,temp);
			temp=sarr2s.func(z);
		}
/*new include*/
		String docdir="docdir_temp/";//key.toString();
	//	String y=x.toString();
	//	String[] z=nv.fn(y,y);
		String[] dt=dtdg.docTopicDistributionGen(z,docdir);
		String dts="";
		/* code for String[] dt to dts */
			for(int i=0;i<dt.length;i++)
			{
				dts=dts+dt[i];
				dts=dts+"@next@";
			}
		output.collect(key, new Text(dts));
	}
}
public static void main(String[] args) throws Exception
{
	JobConf conf = new JobConf(HTopicModel.class);
	conf.setJobName("htopicmodel");
	conf.setOutputKeyClass(LongWritable.class);
	conf.setOutputValueClass(Text.class);
	conf.setMapperClass(LdaMapper.class);
//	conf.setCombinerClass(LdaReducer.class);
	conf.setReducerClass(LdaReducer.class);
	conf.setInputFormat(TextInputFormat.class);
	conf.setOutputFormat(TextOutputFormat.class);
	FileInputFormat.setInputPaths(conf, new Path(args[0]));
	FileOutputFormat.setOutputPath(conf, new Path(args[1]));
	/*StopWordRemoval swr=new StopWordRemoval();
	String fn="docdir_temp/";
	File file = new File(fn);
	String[] filelist=file.list();
	for(int x=0;x<filelist.length;x++)
        {
		String newfile=fn+filelist[x];

		swr.replace(newfile);
	}*/
	long starttime=System.currentTimeMillis();
	JobClient.runJob(conf);
	long endtime=System.currentTimeMillis();
	long time=endtime-starttime;
	System.out.println("The total execution time is : "+time);
}
}
