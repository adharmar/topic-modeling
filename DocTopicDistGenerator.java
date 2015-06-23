package org.myorg.proj;
import java.io.*;

public class DocTopicDistGenerator
{
    public static String[] docTopicDistributionGen(String[] z,String docdir)
    {
        //WordGen newdocgen=new WordGen();
        int [][] docs_words={ {1, 4, 3, 2, 3, 1, 4, 3, 2, 3},
{2, 2, 4, 2, 4, 2, 2, 2, 2},
{1, 6, 5, 6, 0, 1, 6, 5, 6, 0, 1, 6, 5, 6, 0, 0},
{5, 6, 6, 2, 3, 3, 6, 5, 6, 2, 2, 6, 5, 6, 6, 6, 0},
{2, 2, 4, 4, 1, 5, 5, 0},
{5, 4, 2, 3, 4, 5, 6, 6, 5, 4, 3, 2}};//newdocgen.wordListGen(docdir);//2d array of word id's in each document of corpus
        int[][] docs_words_topics=new int[docs_words.length][];//2d array of topic id's in each doc of corpus
        int numWords=7;//newdocgen.hmlength;//hash map length i.e.no.of unique words(vocabulary)
        double [][] topic_counts= new double[docs_words.length][z.length];//count of words(in each doc) belonging to each topic 
        int [] word_topics=new int[numWords];//topic of each word is stored here
        String [] doc_topics = new String[docs_words.length];//topic of each doc
        String[] topic_docs = new String[z.length];//documents under a topic
        for(int i=0;i<z.length;i++)
        {
	    String x=z[i];
            String[] temp=x.split("#");
            for(int j=0;j<temp.length;j++)
            {
		String y=temp[j];
                String[] temp2 = y.split("@");
                word_topics[Integer.parseInt(temp2[0])]=i;
            }
        }
        for(int i=0;i<docs_words.length;i++)
	{
    		docs_words_topics[i] = new int[docs_words[i].length];
		for(int j=0;j<docs_words[i].length;j++)
		{
			docs_words_topics[i][j] = word_topics[docs_words[i][j]];
		}
	}   
        /*for(int i=0;i<docs_words_topics.length;i++)
        {
            for(int j=0;j<docs_words_topics[i].length;j++)
            {
		System.out.println("i val is :"+i+" and j val is :"+j);
                topic_counts[i][word_topics[j]] ++;
            }
        }*/

/*new include*/
	for(int i=0;i<docs_words_topics.length;i++)
	{
		for(int j=0;j<docs_words_topics[i].length;j++)
		{
//			System.out.print(docs_words_topics[i][j]);
//			System.out.print("  -  " + docs_words_topics[i][j]);
			topic_counts[i][docs_words_topics[i][j]]++;
		}
		System.out.println();
	}
/*new include*/

/*new reject	for(int i=0;i<docs_words_topics.length;i++)
	{
		for(int j=0;j<z.length;j++)
		{
			topic_counts[i][word_topics[j]]++;
		}
	}
new reject */


/**/        int count=0;
	    double threshold=.22;
        for(int i=0;i<topic_counts.length;i++)
        {
            count=0;
            doc_topics[i]="";
	    for (int j=0;j<topic_counts[i].length;j++)
            {
                count +=topic_counts[i][j];
            }
            for(int j=0;j<topic_counts[i].length;j++)
            {
                topic_counts[i][j]=topic_counts[i][j]/count;
                if(topic_counts[i][j]>=threshold)
                {
                    doc_topics[i]=doc_topics[i]+String.valueOf(j)+"#"; //docs_words_topics[i][j]
                }
            }
        }
        for(int i=0;i<z.length;i++)
        {
            topic_docs[i]="";
        }
        for(int i=0;i<doc_topics.length;i++)
        {
	    String w=doc_topics[i];
            String[] t = w.split("#");
            for (int j=0;j<t.length;j++)
            {
		int l=Integer.parseInt(t[j]);
                topic_docs[l] = topic_docs[l]+String.valueOf(i)+"@";
            }
        }
        return topic_docs;
    }
}
