package time_20170529;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 * һԪ���Իع��������
 * ��������http://blog.csdn.net/maozefa/article/details/1725535<br>
 * @author 1
 *
 */
public class LinearRegression {

	/**
	 * ���input�ļ�����������
	 */
	public static double[][] data1=null;
	/**
	 * ���X Y���ݡ�x1 y1;x2 y2
	 */
	public static double[][] data2=null;

	// �����Իع鷽��: Y = a + bx
	// data2[rows*2]���飺X, Y��rows������������a, b�����ػع�ϵ��
	// squarePoor[4]�����ط������ָ��: �ع�ƽ���ͣ�ʣ��ƽ���ͣ��ع�ƽ���ʣ��ƽ����
	// ����ֵ��0���ɹ���-1����
	public static void linear_reg(){
		//double[] ab,double[] squarePoor
		double[] ab=new double[2];
		double[] squarePoor=new double[4];
		
		double Lxx = 0, Lxy = 0, xa = 0, ya = 0;
		if (data2 == null || data2.length < 1){
			System.out.println("�������!");
			return;
		}

		for(int i=0; i<data2.length;i++){
			xa=xa+data2[i][0];
			ya=ya+data2[i][1];
		}
		xa=xa/data2.length;// Xƽ��ֵ
		ya=ya/data2.length;// Yƽ��ֵ
		
		for(int i=0;i<data2.length;i++){
			Lxx=Lxx+Math.pow(data2[i][0]-xa, 2);// Lxx = Sum((X - Xa)ƽ��)
			Lxy=Lxy+(data2[i][0]-xa)*(data2[i][1]-ya);// Lxy = Sum((X - Xa)(Y - Ya))
		}
		ab[1] = Lxy/Lxx;              // b = Lxy / Lxx
		ab[0] = ya - ab[1] * xa;      // a = Ya - b*Xa
		
		// �������
		squarePoor[0] = squarePoor[1] = 0;
		for(int i=0;i<data2.length;i++){
			Lxy=ab[0]+ab[1]*data2[i][0];
			squarePoor[0]+=Math.pow(Lxy-ya, 2);// U(�ع�ƽ����)
			squarePoor[1]+=Math.pow(data2[i][1]-Lxy, 2);// Q(ʣ��ƽ����)	
		}
		squarePoor[2]=squarePoor[0];// �ع鷽��
		squarePoor[3]=squarePoor[1]/(data2.length-2);// ʣ�෽��
		
		//������Իع鷽��
		String out=null;
		/*
		out=String.format("�ع鷽��ʽ: Y=%.6f",ab[0]);
		if(ab[1]>=0)
			out+=String.format("+%.6fX", ab[1]);
		else
			out+=String.format("%.6fX", ab[1]);
		*/
		out="a="+ab[0]+"\tb="+ab[1];
		System.out.println(out);
	}

	/**
	 * ��ʾ���Իع�����
	 */
	public static void display(double[] answer, double[] squarePoor, int cols){
		double v;
		int i,j;
		
		//������Իع鷽��
		String out=null;
		double b=0;
		out=String.format("�ع鷽��ʽ: Y=%.6f",answer[0]);
		
		//�˴�Ĭ��cols=2������������
		for(i=1;i<cols;i++){
			if(answer[i]>=0)
				out+=String.format("+%.6fX", answer[i]);
			else
				out+=String.format("%.6fX", answer[i]);
		}
		
		System.out.println(out);
		
		/*
		System.out.println();
		out="�ع������Լ���:\n";
		out+=String.format("�ع�ƽ����: %12.4f  �ع鷽��: %12.4f\n", squarePoor[0], squarePoor[2]);
		out+=String.format("ʣ��ƽ����: %12.4f  ʣ�෽��: %12.4f\n", squarePoor[1], squarePoor[3]);
		out+=String.format("���ƽ����: %12.4f  ��׼���: %12.4f\n", squarePoor[0]+squarePoor[1], Math.sqrt(squarePoor[3]));
		out+=String.format("F   ��  ��: %12.4f  ���ϵ��: %12.4f\n",
				squarePoor[2]/squarePoor[3], Math.sqrt(squarePoor[0]/(squarePoor[0]+squarePoor[1])));
		
		out+=String.format("ʣ�����:\n");
		out+=String.format("�۲�ֵ      ����ֵ      ʣ��ֵ    ʣ��ƽ��\n");
		for(i=0;i<data2.length;i++){
			v = answer[0];
			for(j=1;j<cols;j++)
				v+=data2[i][j-1]*answer[j];
			
			out+=String.format("%12.2f\t%12.2f\t%12.2f\t%12.2f\n", data2[i][j-1],v,data2[i][j-1]-v,(data2[i][j-1]-v)*(data2[i][j-1]-v));
		}
		System.out.println(out+"���Իع���������..");
		*/
	}
	/**
	 * 
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException{
		//�����������Իع鷽��
		generate_reg_batch();
		
		
		/*
		double[] answer=new double[2];
		double[] squarePoor=new double[4];
		//
		String input=null;
		input="./robustTrack2004/apl04rsTDNfw/heart_scale_train_0";
		init_data(input);
		//
		//linear_reg(answer,squarePoor);
		//display(answer,squarePoor,2);
		linear_reg();
		System.out.println("LinearRegression�����������..");
		*/
		
	}

	/**
	 * ��input�ļ��е����ݴ���data1��ά�����С�
	 * @throws IOException 
	 */
	public static void init_data(String input) throws IOException{
		BufferedReader buffReader=null;
		String tempLine=null;
		String[] terms=null;

		//���������ݴ���data1����
		double[] terms2=null;
		ArrayList<double[]> array_data1=new ArrayList<double[]>();
		buffReader=new BufferedReader(new FileReader(input));

		while((tempLine=buffReader.readLine())!=null){
			terms=tempLine.split(" |\t");
			//��terms�е��ַ�������terms2������
			terms2=new double[terms.length];
			for(int i=0;i<terms.length;i++){
				terms2[i]=Double.parseDouble(terms[i]);
			}
			//��terms2����array_data1��
			array_data1.add(terms2);
		}
		buffReader.close();
		//��array_data1�е����ݴ���data1������
		data1=new double[array_data1.size()][];
		for(int i=0;i<array_data1.size();i++){
			data1[i]=array_data1.get(i);
		}
		//System.out.println("��input�ļ��е����ݴ���data1��ά������,�����..");
	}
	
	/**
	 * ��input�ļ��е����ݴ���data1 data2��ά������,���������Իع鷽��
	 * 
	 * @throws IOException 
	 * 
	 */
	public static void generate_reg(String fileName,String packageName) throws IOException{
		
		//��input�ļ��е����ݴ���data1��ά������
		String input=null;
		input="./"+packageName+"/"+fileName;
		init_data(input);
		System.out.println(input);
		
		System.out.print("SD2\t");
		//ʹ��data1�е����ݸ���data2����
		//��data2�������sD2 ap����
		data2=new double[data1.length][2];
		for(int i=0;i<data1.length;i++){
			//����X
			data2[i][0]=data1[i][2];
			//����Y
			data2[i][1]=data1[i][0];
		}
		//�������Իع鷽��
		linear_reg();
		
		System.out.print("WIG\t");
		//��data2�������wIG ap����
		data2=new double[data1.length][2];
		for(int i=0;i<data1.length;i++){
			//����X
			data2[i][0]=data1[i][3];
			//����Y
			data2[i][1]=data1[i][0];
		}
		//�������Իع鷽��
		linear_reg();
		
		System.out.print("SMV\t");
		//��data2�������sMV ap����
		data2=new double[data1.length][2];
		for(int i=0;i<data1.length;i++){
			//����X
			data2[i][0]=data1[i][4];
			//����Y
			data2[i][1]=data1[i][0];
		}
		//�������Իع鷽��
		linear_reg();
		
		System.out.print("NQC\t");
		//��data2�������nQC ap����
		data2=new double[data1.length][2];
		for(int i=0;i<data1.length;i++){
			//����X
			data2[i][0]=data1[i][5];
			//����Y
			data2[i][1]=data1[i][0];
		}
		//�������Իع鷽��
		linear_reg();
		
		System.out.print("C\t");
		//��data2�������c ap����
		data2=new double[data1.length][2];
		for(int i=0;i<data1.length;i++){
			//����X
			data2[i][0]=data1[i][6];
			//����Y
			data2[i][1]=data1[i][0];
		}
		//�������Իع鷽��
		linear_reg();
		
		System.out.print("C2\t");
		//��data2�������c2 ap����
		data2=new double[data1.length][2];
		for(int i=0;i<data1.length;i++){
			//����X
			data2[i][0]=data1[i][7];
			//����Y
			data2[i][1]=data1[i][0];
		}
		//�������Իع鷽��
		linear_reg();
		
		System.out.print("C4\t");
		//��data2�������c4 ap����
		data2=new double[data1.length][2];
		for(int i=0;i<data1.length;i++){
			//����X
			data2[i][0]=data1[i][8];
			//����Y
			data2[i][1]=data1[i][0];
		}
		//�������Իع鷽��
		linear_reg();
		//System.out.println("��input�ļ��е����ݴ���data1��ά�����в��������Իع鷽��,�����..");
	}
	
	/**
	 * ����packageName�ļ����е�heart_scale_train_0,1,2,3,4�ļ�,�����Ӧ�����Իع鷽��
	 * @param packageName
	 * @throws IOException 
	 */
	public static void generate_reg_batch() throws IOException{
		String packageName=null;
		String fileName=null;
		
		//
		File file=null;
		File[] file_list=null;
		file=new File("./robustTrack2004");
		file_list=file.listFiles();
		for(int i=0;i<file_list.length;i++){
			packageName="robustTrack2004/"+file_list[i].getName();
			//������ļ�����ÿ���ļ�(heart_scale_train_i)�Ļع鷽��
			System.out.println("\n\n"+(i+1)+"��packageName="+packageName);
			for(int j=0;j<5;j++){
				fileName="heart_scale_train_"+j;
				generate_reg(fileName,packageName);
			}
		}
		//System.out.println("�����������Իع鷽��,�����..");
	}

}
