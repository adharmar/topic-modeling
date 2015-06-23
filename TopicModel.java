import java.io.*;
import java.util.*;
import java.lang.*;

public class TopicModel
{

	public static void main(String args[]) throws IOException
	{
		StopWordRemoval swr=new StopWordRemoval();
		LdaGibbsSampler lgs=new LdaGibbsSampler();
		NaiveMerger nm=new NaiveMerger();
		Naive nv=new Naive();
		DocTopicDistGenerator dtdg=new DocTopicDistGenerator();		
		BufferedReader br=new BufferedReader(new InputStreamReader(System.in));
		System.out.print("Enter the directory containing the corpus : ");	
		String fn=br.readLine();

		File file = new File(fn);
 		String[] filelist=file.list();
		for(int x=0;x<filelist.length;x++)
	        {
			String newfile=fn+filelist[x];

			swr.replace(newfile);
		}
		
		//String fstr=lgs.lda();
		//System.out.println("\n\n\n"+fstr);
		//String[] z=nm.mergefn(fstr,fstr);
		//for(int i=0;i<z.length;i++)
		//{
		//	System.out.println(i+"    |    "+z[i]);
		//}
		float[][] phi1=lgs.lda();
		/*float[][] phi2={{(float)0.21820676,(float)0.29846096,(float)0.2832222,(float)0.20011006},{(float)0.2489743,(float)0.3010885,(float)0.25764072,(float)0.19229649},{(float)0.21819186,(float)0.31168348,(float)0.28398708,(float)0.18613757}};*/
		float[][] phi2={{(float)0.2,(float)0.3,(float)0.28,(float)0.22},{(float)0.02,(float)0.30,(float)0.4,(float)0.28},{(float)0.15,(float)0.4,(float)0.3,(float)0.15}};  
		
		/*for(int i=0;i<phi.length;i++)
		{
			for(int j=0;j<phi[i].length;j++)
			{
				System.out.print(phi[i][j]+"  ");
			}
			System.out.println();
		}*/
		String[] z=nv.fn(phi1,phi2);
		String[] dt=dtdg.docTopicDistributionGen(z);
		System.out.println("The document topic distribution is : ");
		for(int i=0;i<dt.length;i++)
		{
			System.out.println(i+"  |  "+dt[i]);
		}		
	}
}
			
