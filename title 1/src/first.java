import java.util.Random;
import java.math.*;
import java.util.Scanner; 




public class first {
	public static boolean isPrime(int a) {
		boolean flag = true;
		if (a < 2) {// 素数不小于2
			return false;
		} else {
			for (int i = 2; i <= Math.sqrt(a); i++) {
				if (a % i == 0) {// 若能被整除，则说明不是素数，返回false
					flag = false;
					break;// 跳出循环
				}
			}
		}
		return flag;
	}
	
	public static int encode(int k_,int n_,int d_)
	{
		int p_;
		if(d_>n_)
			p_=d_+1;
		else
			p_=n_+1;
		while(isPrime(p_)==false)
		{
			p_++;
		}
		System.out.println("p="+p_);
		Random df = new Random();
		int[] xishu=new int[k_];
		int k1=k_-1;
//		xishu[1]=2;
//		xishu[2]=7;
		
		
		for(int i=1;k1>0;k1--)
		 {
			 xishu[i]=df.nextInt(p_);
			 //System.out.println(xishu[i]);
			 i++;
		 }
		 //求出系数的方法	
		
		k1=k_-1;
		System.out.print("q(x)=(");
		for(int i=k1;k1>0;k1--)
		{
			System.out.print(xishu[i]+"x^"+i+"+");
			i--;
			if(k1==1)
				System.out.println(d_+")mod"+d_);
		}
		// 输出函数p(x)
		 
		//计算n_块D
		System.out.println("加密结果为：");
		double[] D=new double[n_+1];
		for(int i=1;i<=n_;i++)
		{
			D[i]=d_;
			for(int j=1;j<k_;j++)
			{
				D[i]=D[i]+xishu[j]*Math.pow(i,j);
			}
			D[i]=D[i]%p_;
			System.out.println("D["+i+"]="+D[i]);
		}
		return p_;
	}
	
	public static void decode(int k_,int n_,int d_,int p_)
	{
		//加密完成，下面开始解密
				System.out.println("加密完成，下面开始解密过程");
				System.out.println("你至少需要输入"+k_+"块数据才可以进行解密");

				double[] innum=new double[k_+1];
				double[] input=new double[k_+1];
				Scanner scan = new Scanner(System.in);
				for(int i=1;i<=k_;i++)
				{
					System.out.println("请输入你获得的D的下标(范围为1-"+n_+")");
					innum[i]=scan.nextInt();
					System.out.println("请输入你获得的D的数值(范围为1-"+p_+")");
					input[i]=scan.nextInt();
					if(i==k_)
					{
						System.out.println("输入完成！");
						break;
					}
					System.out.println("请继续输入！");
				}
				
				//input[1]=1;input[2]=5;input[3]=4;
				double[] up=new double[k_+1];
				double[] xishu_=new double[k_+1];//代表分子和之前的每一个系数
				//先求系数
				for(int i=1;i<=k_;i++)
				{
					double temp=1;
					for(int j=1;j<=k_;j++)
					{
						if(j!=i)
						{
							temp=temp*(innum[i]-innum[j]);
						}
					}
					//求出了下面的数，下面进行inv(x,y)运算
					while(temp<0)
					{
						temp=temp+p_;
					}
					for(int k=1;k<p_;k++)
					{
						if(((temp*k)%p_)==1)
						{
							temp=k;
							break;
						}
							
					}
					
					xishu_[i]=input[i]*temp;
					//System.out.println(xishu_[i]);
				}
				//输出公式
				System.out.println("开始解密...");
				for(int i=1;i<=k_;i++)
				{
					System.out.print("q(D"+(int)innum[i]+")="+(int)xishu_[i]);
					for(int j=1;j<=k_;j++)
					{
						if(j!=i)
						{
							System.out.print("(x-"+(int)innum[j]+")");
						}
						if(j==k_)
						{
							System.out.println();
						}
					}
				}
				//输出完成，现在计算分子(其实主要是计算系数)
				
				//先输出q(x)

				System.out.print("q(x)=");
				for(int i=1;i<=k_;i++)
				{
					System.out.print((int)(xishu_[i]%p_));
					for(int j=1;j<=k_;j++)
					{
						if(j!=i)
						{
							System.out.print("(x-"+(int)innum[j]+")");
						}
						if(j==k_&&i!=k_)
						{
							System.out.print("+");
						}
					}
				}
				//输出完成，现在我们求D，也就是最后的常数
				System.out.println();
				double decoded=0;
				for(int i=1;i<=k_;i++)
				{
					double temp=1;
					xishu_[i]=xishu_[i]%p_;
					for(int j=1;j<=k_;j++)
					{
						if(j!=i)
						{
							temp=-1*temp*innum[j];
						}
						if(j==k_)
						{
							temp=temp*xishu_[i];
						}
					}
					decoded=decoded+temp;
				}
				if(decoded>p_)
					decoded=decoded%p_;
				System.out.println("解密出的D为:"+decoded);
	}
	
	public static void main(String args[])
	{
		System.out.println("请输入k,n,D(需要加密的数据)");
		int k_=3,n_=5,d_=11,p_;
		Scanner scan = new Scanner(System.in);
		k_=scan.nextInt();
		n_=scan.nextInt();
		d_=scan.nextInt();
		p_=encode(k_,n_,d_);
		
		decode(k_,n_,d_,p_);
		
	}
}
