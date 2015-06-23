import java.io.*;
import java.util.*;
import java.lang.*;
import mpi.*;

public class TopicModel
{

	public static void main(String args[]) throws IOException
	{
		long starttime=System.currentTimeMillis();
		
		MPI.Init(args);
		int me = MPI.COMM_WORLD.Rank();
		int size = MPI.COMM_WORLD.Size();		


		StopWordRemoval swr=new StopWordRemoval();
		WordGen wg=new WordGen();
		LdaGibbsSampler lgs=new LdaGibbsSampler();
		NaiveMerger nm=new NaiveMerger();
		Naive nv=new Naive();
		DocTopicDistGenerator dtdg=new DocTopicDistGenerator();		
		BufferedReader br=new BufferedReader(new InputStreamReader(System.in));
		//System.out.print("Enter the directory containing the corpus : ");	
		String fn="";		
		if(me==0)
		{
			fn="docdir/";
		}
		else
		{
			fn="docdir/";//br.readLine();
		}
		File file = new File(fn);
 		String[] filelist=file.list();
		for(int x=0;x<filelist.length;x++)
	        {
			String newfile=fn+filelist[x];

			swr.replace(newfile,me);
		}
		
		//String fstr=lgs.lda();
		//System.out.println("\n\n\n"+fstr);
		//String[] z=nm.mergefn(fstr,fstr);
		//for(int i=0;i<z.length;i++)
		//{
		//	System.out.println(i+"    |    "+z[i]);
		//}
		
		//if(me==0)
		//{
			String[][] wordlist1=wg.wordListGen(0);
		//}
		//else
		//{
			String[][] wordlist2=wg.wordListGen(1);		
		//}
		int s=0;
		//int i,j;
		HashMap hm = new HashMap();
		int[][] documents1=new int[wordlist1.length][];
		int[][] documents2=new int[wordlist2.length][];
		for(int i=0;i<wordlist1.length;i++)
		{
			documents1[i] = new int[wordlist1[i].length];
			for(int j=0;j<wordlist1[i].length;j++)
			{
				if(hm.containsKey(wordlist1[i][j]) == false)
				{
					hm.put(wordlist1[i][j],new Integer(s));
					s++;
				}
				Object v=hm.get(wordlist1[i][j]);			
				documents1[i][j]=Integer.parseInt(v.toString());	
				//System.out.println(documents[i][j]);
			}
		}
		for(int i=0;i<wordlist2.length;i++)
		{
			documents2[i] = new int[wordlist2[i].length];
			for(int j=0;j<wordlist2[i].length;j++)
			{
				if(hm.containsKey(wordlist2[i][j]) == false)
				{
					hm.put(wordlist2[i][j],new Integer(s));
					s++;
				}
				Object v=hm.get(wordlist2[i][j]);			
				documents2[i][j]=Integer.parseInt(v.toString());	
				//System.out.println(documents[i][j]);
			}
		}
		int hmlength=hm.size();
		//System.out.println("The hash map length is : "+hmlength);
		//return documents;
		

		int K=lgs.K;		
	
		float[][] phi1=new float[K][hmlength];
		float[][] phi2=new float[K][hmlength];
		if(me==0)
		{
			phi1=lgs.lda(documents1,hmlength,me);
		}
		else
		{
			phi2=lgs.lda(documents2,hmlength,me);
		}


		/*if(me==0)
		{
			phi1=lgs.lda(me);
		}
		/*float[][] phi2={{(float)0.21820676,(float)0.29846096,(float)0.2832222,(float)0.20011006},{(float)0.2489743,(float)0.3010885,(float)0.25764072,(float)0.19229649},{(float)0.21819186,(float)0.31168348,(float)0.28398708,(float)0.18613757}};
		float[][] phi2={{(float)0.2,(float)0.3,(float)0.28,(float)0.22},{(float)0.02,(float)0.30,(float)0.4,(float)0.28},{(float)0.15,(float)0.4,(float)0.3,(float)0.15}}; 
		else
		{
			phi2=lgs.lda(me);
		}*/



		/*for(int i=0;i<phi.length;i++)
		{
			for(int j=0;j<phi[i].length;j++)
			{
				System.out.print(phi[i][j]+"  ");
			}
			System.out.println();
		}*/
		if(me==0) /*only the master process merges and finds doc-topic distribution*/
		{
		String[] z=nv.fn(phi1,phi2);
		
		//documentsx[][]=documents1[][]+documents2[][];
		int h;
		int [][] documentsx=new int[documents1.length+documents2.length][];
		for(h=0;h<documents1.length;h++)
		{
			documentsx[h]=new int[documents1[h].length];
			for(int j=0;j<documents1[h].length;j++)
			{
				documentsx[h][j]=documents1[h][j];
			}
		}
		for(int g=0;g<documents2.length;g++,h++)
		{
			documentsx[h]=new int[documents2[g].length];
			for(int j=0;j<documents2.length;j++)
			{
				documentsx[h][j]=documents2[g][j];
			}
		}		
		String[] dt=dtdg.docTopicDistributionGen(z,documentsx,hmlength,me);
		System.out.println("The document topic distribution is : ");
		for(int i=0;i<dt.length;i++)
		{
			System.out.println(i+"  |  "+dt[i]);
		}	
	        long endtime=System.currentTimeMillis();
	        long time=endtime-starttime;
	        System.out.println("The total execution time is : "+time);
		}
	
	}
}
			
