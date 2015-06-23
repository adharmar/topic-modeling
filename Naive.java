package org.myorg.proj;
import java.io.*;

public class Naive
{
	public static String[] fn(String p,String r)//(float[][] phi1,float[][] phi2)
	{
		String[] t=p.split("#");
		float[][] phi1=new float[t.length][];
		for(int i=0;i<t.length;i++)
		{
			String[] t1=t[i].split("@");
			phi1[i]=new float[t1.length];
			for(int j=0;j<t1.length;j++)
			{
				phi1[i][j]=Float.parseFloat(t1[j]);
			}
		}
		String[] t2=r.split("#");
                float[][] phi2=new float[t2.length][];
                for(int i=0;i<t2.length;i++)
                {
                        String[] t3=t2[i].split("@");
                        phi2[i]=new float[t3.length];
                        for(int j=0;j<t3.length;j++)
                        {
                                phi2[i][j]=Float.parseFloat(t3[j]);
                        }
                }

		int f=0;
		float[] diff=new float[phi1.length*phi2.length];
		System.out.println();
		for(int i=0;i<phi1.length;i++)
		{
			//System.out.println();
			//System.out.print(i);
			for(int j=0;j<phi1.length;j++)
			{
				//System.out.println();
				//System.out.print(j);
				//System.out.println();
				for(int k=0;k<phi1[i].length;k++)
				{
					//System.out.print(k+"  ");
					//System.out.println("the 1st phi value is :"+phi1[i][k]);
					//System.out.println("the 2nd phi value is :"+phi2[j][k]);
					diff[f] +=phi1[i][k]-phi2[j][k];
				}
				f++;
			}
		}
		for(f=0;f<diff.length;f++)
		{
			System.out.println(diff[f]);
		}
		System.out.println();
		System.out.println("Finding Similar Topics........");
		int h=0;
		float[][] diff2=new float[phi1.length][phi1[0].length];
		int[][] simtopics=new int[phi1.length][2];
		for(int i=0;i<phi1.length;i++)
		{
			for(int j=0;j<phi1.length;j++)
			{
				diff2[i][j]=diff[h];
				//System.out.println(diff[h] +" and "+diff2[i][j]);
				if(!(diff2[i][j]<0 || diff2[i][j]>0))
				{
					simtopics[i][0]=i;simtopics[i][1]=j;
					System.out.println(simtopics[i][0]+" and "+simtopics[i][1]);//+"i.e. "+i+" and "+j);
				}
				h++;			
			}
		}

		System.out.println();
	
		String z1str=fnstringer(phi1);
		String z2str=fnstringer(phi2);
		System.out.println(z1str);
		System.out.println(z2str);

		z1str=z1str+phi1.length;
		z2str=z2str+phi2.length;
	
		String newtemp="";
		String[] temp=z2str.split("#");
		for(int i=0;i<temp.length-1;i++)
		{
			String newstr=temp[i];
			//System.out.println(newstr);
			String[] temp2=newstr.split("@");

			for(int g=0;g<simtopics.length;g++)
			{
				int q=Integer.parseInt(temp2[1]);
				if(simtopics[g][1]==q)
				{
					temp2[1]="";
					temp2[1]+=simtopics[g][0];			
				}
			}
			newtemp+=temp2[0]+"@"+temp2[1]+"@"+temp2[2]+"#";
						
			//int x=Integer.parseInt(temp[1]);
			//z[x]=z[x]+newtemp+"#";
		}
		newtemp=newtemp+phi2.length;
		System.out.println(newtemp);
		
		NaiveMerger nm=new NaiveMerger();
		String[] z=nm.mergefn(z1str,newtemp);
		for(int i=0;i<z.length;i++)
		{
			System.out.println(i+"    |    "+z[i]);
		}
		return z;
	}
	public static String fnstringer(float[][] phi1)
	{
		int[] topick_p=new int[phi1[0].length];
		int[] wordw_p=new int [phi1[0].length];
		int z=0;
		String z1str="";
		double[] maxPhi=new double[phi1[0].length];
		for(int f=0;f<phi1[0].length;f++)
		{
			maxPhi[f]=Double.MIN_VALUE;
		}
		for(int g=0;g<phi1[0].length;g++)
		{
			for(int h=0;h<phi1.length;h++)
			{
				if(phi1[h][g]>maxPhi[z])
				{		
		                	maxPhi[z] = phi1[h][g];
					topick_p[z]=h;
					wordw_p[z]=g;
				}
			}
			z1str=z1str+maxPhi[z]+"@"+topick_p[z]+"@"+wordw_p[z]+"#";
			z++;
		}
		return z1str;
	}
}

		/*String[] temp1=str1.split("#");
		String[] temp2=str2.split("#");
		int K=Integer.parseInt(temp1[temp1.length-1]);
		String[][] table=new String[K][2];
		for(int i=0;i<K;i++)
		{
			for(int j=0;j<2;j++)
			{
				table[i][j]="";
			}
		}
		for(int i=0;i<temp1.length-1;i++)
		{
			String newstr=temp1[i];
			String[] temp=newstr.split("@");
			table[temp[1]][0]+=temp[2];
			table[temp[1]][1]+=temp[0];			
		}*/

