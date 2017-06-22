package time_20170529;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.ListIterator;

/**
 * ʹ�����Իع鷽������baseline���������ܡ�
 * @author 1
 *
 */
public class Process_baseline_2 {
	
	public static ArrayList<Run_linear> ArrayRun=null;
	
	/**
	 * ��packageName�ļ�����,��AP��Ϣ����heart_scale�ļ���,�γ�heart2�ļ���
	 * @param runId
	 * @param packageName
	 * @throws IOException 
	 */
	public static void add_ap_to_heart_scale(String runId,String out_name,String packageName) throws IOException{
		BufferedReader buffReader=null;
		BufferedWriter buffWriter=null;
		StringBuffer info=null;
		String tempLine=null;
		String[] terms=null;
		
		//��map.normalized.runId�ļ��е�AP��Ϣ����array_info��
		ArrayList<StringBuffer> array_info=new ArrayList<StringBuffer>();
		buffReader=new BufferedReader(new FileReader("./"+packageName+"/map.normalized."+runId));
		while((tempLine=buffReader.readLine())!=null){
			terms=tempLine.split(" |\t");
			info=new StringBuffer(terms[4]);
			array_info.add(info);
		}
		buffReader.close();
		
		//��heart_scale�ļ��е���Ϣ����array_info��
		buffReader=new BufferedReader(new FileReader("./"+packageName+"/heart_scale"));
		int pointer=0;//array_info�����ָ��
		while((tempLine=buffReader.readLine())!=null){
			info=array_info.get(pointer++);
			info.append(" "+tempLine);
		}
		buffReader.close();
		
		//��array_info�е���Ϣ����heart2�ļ���
		buffWriter=new BufferedWriter(new FileWriter("./"+packageName+"/"+out_name));
		for(int i=0;i<array_info.size();i++){
			info=array_info.get(i);
			buffWriter.write(info.toString()+"\n");
		}
		buffWriter.close();
		System.out.println("��AP��Ϣ����heart_scale�ļ��в��γ�heart2�ļ�,�����..");
	}
	
	/**
	 * ��packageName�ļ�����,ȥ����heart2�ļ��е�<index:>��Ϣ
	 * @param packageName
	 * @throws IOException 
	 */
	public static void remove_index(String packageName) throws IOException{
		BufferedReader buffReader=null;
		BufferedWriter buffWriter=null;
		String tempLine=null;
		ArrayList<String> array_info=new ArrayList<String>();
		
		buffReader=new BufferedReader(new FileReader("./"+packageName+"/heart2"));
		while((tempLine=buffReader.readLine())!=null){
			tempLine=tempLine.replaceAll("[\\d]+:", "");
			array_info.add(tempLine);
		}
		buffReader.close();
		
		//��array_info�е���Ϣ����heart2�ļ���
		buffWriter=new BufferedWriter(new FileWriter("./"+packageName+"/heart2"));
		for(int i=0;i<array_info.size();i++){
			tempLine=array_info.get(i);
			buffWriter.write(tempLine+"\n");
		}
		buffWriter.close();
		System.out.println("ȥ����heart2�ļ��е�<index:>��Ϣ,�����..");
	}
	
	/**
	 * 
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException{
		//�������Իع��ļ�,��heart_scale_test_x�ļ�,������Ӧ��Ԥ������ļ���
		task_control();
		
		
	}
	/**
	 * �����Իع�Ľ�������ڴ�
	 * @throws IOException 
	 * 
	 */
	public static ArrayList<Run_linear> load_linear(String input) throws IOException{
		BufferedReader buffReader=null;
		String tempLine=null;
		
		//��input�ļ��еĽ��,�����ַ�����,����array_info��
		buffReader=new BufferedReader(new FileReader(input));
		ArrayList<String> array_info=new ArrayList<String>();
		while((tempLine=buffReader.readLine())!=null){
			//��tempLineΪ���ַ���,������
			if(tempLine.equalsIgnoreCase("")) continue;
			array_info.add(tempLine);
		}
		buffReader.close();
		
		//����array_info�е���Ϣ,��array_run��ֵ
		ArrayList<Run_linear> array_run=new ArrayList<Run_linear>();
		Run_linear run_linear=null;
		ListIterator<String> iter=array_info.listIterator();
		while(iter.hasNext()){
			run_linear=new Run_linear(iter);
			array_run.add(run_linear);
		}
		System.out.println("�����Իع�Ľ�������ڴ�,�����..");
		return array_run;
	}
	
	/**
	 * ���һ��heart_scale_test_x�ļ�,ʹ�����Իع鷽��(y=a+bx)����������Ԥ�������Ϣ,<br>
	 * ֮�������Ϣ�����ļ���
	 * @throws IOException 
	 */
	public static void generate_prediction(int i_heart_scale_test,int i_packageName,String packageName) throws IOException{
		String input=null;
		BufferedReader buffReader=null;
		String tempLine=null;
		
		//��heart_scale_test_x�ļ��е���Ϣ����data������
		String[] terms=null;
		double[][] data=null;
		double[] terms2=null;
		ArrayList<double[]> array_data=new ArrayList<double[]>();
		//��heart_scale_test_x���ݴ���array_data
		input="./"+packageName+"/heart_scale_test_"+i_heart_scale_test;
		buffReader=new BufferedReader(new FileReader(input));
		while((tempLine=buffReader.readLine())!=null){
			terms=tempLine.split(" |\t");
			//��terms�е��ַ�������terms2������
			terms2=new double[terms.length];
			for(int i=0;i<terms.length;i++){
				terms2[i]=Double.parseDouble(terms[i]);
			}
			array_data.add(terms2);
		}
		buffReader.close();
		//ʹ��array_data��data���鸳ֵ
		data=new double[array_data.size()][];
		for(int i=0;i<array_data.size();i++){
			data[i]=array_data.get(i);
		}
		
		/****************/
		//��ÿ��Ԥ���㷨��Ԥ����𶼴���classLabel������
		int[][] classLabel=null;//classLabel����ĸ��зֱ�Ϊ<standard,sD2,wIG,sMV,nQC,c,c2,c4>
		double y=0;
		double a=0;
		double b=0;
		//��ÿ��Ԥ���㷨�ع��Ӧ��apֵ������predictedAP�����У�by Zoey��
		double[][] predictedAP=null;
		
		//����׼������classLabel��һ����
		classLabel=new int[data.length][8];//classLabel����8��ֵ
		for(int i=0;i<data.length;i++){
			classLabel[i][0]=(int)data[i][1];
		}
		
		//����ʵAPֵ����predictedAP��һ���У�by Zoey)
		predictedAP=new double[data.length][8];
		for(int i=0;i<data.length;i++){
			predictedAP[i][0]=data[i][0];
		}
		
		//����sD2�е�yֵ,�ó�Ԥ����𲢴���classLabel
		a=ArrayRun.get(i_packageName).heart_train[i_heart_scale_test].sD2[0];
		b=ArrayRun.get(i_packageName).heart_train[i_heart_scale_test].sD2[1];
		for(int i=0;i<data.length;i++){
			y=a+b*data[i][2];
			//��yֵ����predictedAP��by Zoey��
			predictedAP[i][1]=y;
			if(y<=0.2) classLabel[i][1]=1;
			if(y>0.2&&y<=0.4) classLabel[i][1]=2;
			if(y>0.4) classLabel[i][1]=3;
		}
		//����wIG�е�yֵ,�ó�Ԥ����𲢴���classLabel
		a=ArrayRun.get(i_packageName).heart_train[i_heart_scale_test].wIG[0];
		b=ArrayRun.get(i_packageName).heart_train[i_heart_scale_test].wIG[1];
		for(int i=0;i<data.length;i++){
			y=a+b*data[i][3];
			//��yֵ����predictedAP��by Zoey��
			predictedAP[i][2]=y;
			if(y<=0.2) classLabel[i][2]=1;
			if(y>0.2&&y<=0.4) classLabel[i][2]=2;
			if(y>0.4) classLabel[i][2]=3;
		}
		//����sMV�е�yֵ,�ó�Ԥ����𲢴���classLabel
		a=ArrayRun.get(i_packageName).heart_train[i_heart_scale_test].sMV[0];
		b=ArrayRun.get(i_packageName).heart_train[i_heart_scale_test].sMV[1];
		for(int i=0;i<data.length;i++){
			y=a+b*data[i][4];
			//��yֵ����predictedAP��by Zoey��
			predictedAP[i][3]=y;
			if(y<=0.2) classLabel[i][3]=1;
			if(y>0.2&&y<=0.4) classLabel[i][3]=2;
			if(y>0.4) classLabel[i][3]=3;
		}
		//����nQC�е�yֵ,�ó�Ԥ����𲢴���classLabel
		a=ArrayRun.get(i_packageName).heart_train[i_heart_scale_test].nQC[0];
		b=ArrayRun.get(i_packageName).heart_train[i_heart_scale_test].nQC[1];
		for(int i=0;i<data.length;i++){
			y=a+b*data[i][5];
			//��yֵ����predictedAP��by Zoey��
			predictedAP[i][4]=y;
			if(y<=0.2) classLabel[i][4]=1;
			if(y>0.2&&y<=0.4) classLabel[i][4]=2;
			if(y>0.4) classLabel[i][4]=3;
		}
		//����c�е�yֵ,�ó�Ԥ����𲢴���classLabel
		a=ArrayRun.get(i_packageName).heart_train[i_heart_scale_test].c[0];
		b=ArrayRun.get(i_packageName).heart_train[i_heart_scale_test].c[1];
		for(int i=0;i<data.length;i++){
			y=a+b*data[i][6];
			//��yֵ����predictedAP��by Zoey��
			predictedAP[i][5]=y;
			if(y<=0.2) classLabel[i][5]=1;
			if(y>0.2&&y<=0.4) classLabel[i][5]=2;
			if(y>0.4) classLabel[i][5]=3;
		}
		//����c2�е�yֵ,�ó�Ԥ����𲢴���classLabel
		a=ArrayRun.get(i_packageName).heart_train[i_heart_scale_test].c2[0];
		b=ArrayRun.get(i_packageName).heart_train[i_heart_scale_test].c2[1];
		for(int i=0;i<data.length;i++){
			y=a+b*data[i][7];
			//��yֵ����predictedAP��by Zoey��
			predictedAP[i][6]=y;
			if(y<=0.2) classLabel[i][6]=1;
			if(y>0.2&&y<=0.4) classLabel[i][6]=2;
			if(y>0.4) classLabel[i][6]=3;
		}
		//����c4�е�yֵ,�ó�Ԥ����𲢴���classLabel
		a=ArrayRun.get(i_packageName).heart_train[i_heart_scale_test].c4[0];
		b=ArrayRun.get(i_packageName).heart_train[i_heart_scale_test].c4[1];
		for(int i=0;i<data.length;i++){
			y=a+b*data[i][8];
			//��yֵ����predictedAP��by Zoey��
			predictedAP[i][7]=y;
			if(y<=0.2) classLabel[i][7]=1;
			if(y>0.2&&y<=0.4) classLabel[i][7]=2;
			if(y>0.4) classLabel[i][7]=3;
		}
		
		/********************/
		//��classLabel��������Ϣ�����ļ�
		BufferedWriter buffWriter=null;
		tempLine="";//����tempLine,��������Ϊ���ַ���
		input="./"+packageName+"/heart_scale_test_"+i_heart_scale_test+"_regression";
		buffWriter=new BufferedWriter(new FileWriter(input));
		for(int i=0;i<classLabel.length;i++){
			tempLine="";//��tempLine��Ϊ���ַ���
			for(int j=0;j<classLabel[i].length;j++){
				tempLine=tempLine+classLabel[i][j]+"\t";
			}
			//ȥ��tempLineĩβ��\t
			tempLine=tempLine.trim();
			buffWriter.write(tempLine+"\n");
		}
		buffWriter.close();
		System.out.println("���һ��heart_scale_test_x�ļ�,����Ԥ�������Ϣ�������ļ�,�����..");
	
		/*********************/
		//��predictedAP��������Ϣ�����ļ���by Zoey��
		BufferedWriter bfWriter=null;
		tempLine="";
		input="./"+packageName+"/heart_scale_test_"+i_heart_scale_test+"_regression_AP_value";
		bfWriter=new BufferedWriter(new FileWriter(input));
		for(int i=0;i<predictedAP.length;i++){
			tempLine="";
			for(int j=0;j<predictedAP[i].length;j++){
				tempLine=tempLine+predictedAP[i][j]+"\t";
			}
			
			tempLine=tempLine.trim();
			bfWriter.write(tempLine+"\n");
		}
		bfWriter.close();
		System.out.println("���һ��heart_scale_test_x�ļ��������ع�Ԥ�⵽��APֵ�������ļ��������..");
	}
	
	/**
	 * ���ݵõ������Իع鷽��,��������ÿ��heart_scale_test_x�ļ���Ӧ��Ԥ�����,�������ļ�
	 * @throws IOException 
	 */
	public static void generate_prediction_batch() throws IOException{
		//Ϊÿ��packageName�µ�heart_scale_test_x����Ԥ�����
		BufferedReader buffReader=null;
		String tempLine=null;
		String input=null;
		String runId=null;
		String packageName=null;//��Ϊgenerate_prediction()���������
		int i_packageName=-1;//packageName�ı��,��ʼʱΪ-1
		
		input="./robustTrack2004/runId.txt";
		buffReader=new BufferedReader(new FileReader(input));
		while((tempLine=buffReader.readLine())!=null){
			runId=tempLine.split("\\.")[1];
			packageName="robustTrack2004/"+runId;
			i_packageName++;
			//ÿ��packageName����5��heart_scale_test_x�ļ�
			for(int i=0;i<5;i++){
				generate_prediction(i,i_packageName,packageName);
			}
		}
		buffReader.close();
		System.out.println("��������ÿ��heart_scale_test_x�ļ���Ӧ��Ԥ�����,�������ļ�,�����..");
	}
	
	/**
	 * ���һ��heart_scale_test_x_regression�ļ�,����׼ȷ����Ϣ
	 * @throws IOException 
	 */
	public static double[][] compute_accuracy(String fileName,String packageName) throws IOException{
		//��input�ļ�����classLabel����
		BufferedReader buffReader=null;
		String input=null;
		String tempLine=null;
		String[] terms=null;
		int[] terms2=null;
		/**
		 * classLabel����ĸ�ʽΪ:
		 * standard sD2 wIG sMV nQC c c2 c4
		 *     x     x   x   x   x  x  x  x
		 *     x     x   x   ...
		 * 
		 */
		int[][] classLabel=null;
		ArrayList<int[]> array_classLabel=new ArrayList<int[]>();
		
		input="./"+packageName+"/"+fileName;
		buffReader=new BufferedReader(new FileReader(input));
		while((tempLine=buffReader.readLine())!=null){
			terms=tempLine.split(" |\t");
			terms2=new int[terms.length];
			for(int i=0;i<terms.length;i++){
				terms2[i]=Integer.parseInt(terms[i]);
			}
			array_classLabel.add(terms2);
		}
		buffReader.close();
		//��array_classLabel�е����ݴ���classLabel������
		classLabel=new int[array_classLabel.size()][];
		for(int i=0;i<array_classLabel.size();i++){
			classLabel[i]=array_classLabel.get(i);
		}
		
		/**************************/
		//����ÿ��Ԥ�ⷽ�������Ԥ��׼ȷ��,������accuracy������,Ԥ�ⷽ������7��
		int labe_1=0;
		int labe_1_matched=0;
		int labe_2=0;
		int labe_2_matched=0;
		int labe_3=0;
		int labe_3_matched=0;
		/**
		 * accuracy����ĸ�ʽΪ:<br>
		 *         sD2 wIG sMV nQC c c2 C4
		 * overall  x   x   x   x  x x  x
		 * hard     x   x   x   ...
		 * medium
		 * easy
		 * (hard+easy)
		 * 
		 */
		double[][] accuracy=new double[5][7];
		
		//Ԥ���㷨����7��,k_methodΪԤ���㷨�ı��(��0��ʼ���)
		for(int k_method=0;k_method<7;k_method++){
			for(int i=0;i<classLabel.length;i++){
				if(classLabel[i][0]==1){
					labe_1++;
					if(classLabel[i][0]==classLabel[i][1+k_method]) labe_1_matched++;
				}
				if(classLabel[i][0]==2){
					labe_2++;
					if(classLabel[i][0]==classLabel[i][1+k_method]) labe_2_matched++;
				}
				if(classLabel[i][0]==3){
					labe_3++;
					if(classLabel[i][0]==classLabel[i][1+k_method]) labe_3_matched++;
				}
			}
			//����׼ȷ��
			accuracy[0][k_method]=(double)(labe_1_matched+labe_2_matched+labe_3_matched)/(labe_1+labe_2+labe_3);
			accuracy[1][k_method]=(double)labe_1_matched/labe_1;
			accuracy[2][k_method]=(double)labe_2_matched/labe_2;
			accuracy[3][k_method]=(double)labe_3_matched/labe_3;
			accuracy[4][k_method]=(double)(labe_1_matched+labe_3_matched)/(labe_1+labe_3);
			//����labe_x,labe_x_matched����
			labe_1=labe_1_matched=0;
			labe_2=labe_2_matched=0;
			labe_3=labe_3_matched=0;
		}
		
		/**************************/
		//��accuracy�����е����ݴ����ļ���
		BufferedWriter buffWriter=null;
		
		input="./"+packageName+"/"+fileName+"_accuracy";
		buffWriter=new BufferedWriter(new FileWriter(input));
		for(int i=0;i<accuracy.length;i++){
			tempLine="";//����tempLine
			for(int j=0;j<accuracy[i].length;j++){
				tempLine=tempLine+accuracy[i][j]+"\t";
			}
			//ȥ��tempLineĩβ��\t
			tempLine=tempLine.trim();
			buffWriter.write(tempLine+"\n");
		}
		buffWriter.close();
		System.out.println("���һ��heart_scale_test_x_regression�ļ�,����׼ȷ����Ϣ�������ļ�,�����..");
		
		//����accuracy����
		return accuracy;
	}
	/**
	 * ��������heart_scale_test_x_regression�ļ���Ӧ��׼ȷ����Ϣ<br>
	 * @throws IOException 
	 * 
	 */
	public static void compute_accuracy_batch() throws IOException{
		//��������׼ȷ���ļ�,������Ԥ�ⷽ���ڸ����н���ϵ�ƽ��׼ȷ����Ϣ
		BufferedReader buffReader=null;
		String tempLine=null;
		String input=null;
		String runId=null;
		String packageName=null;
		String fileName=null;
		double[][][] accu=null;//���һ�����н����Ӧ��׼ȷ��
		ArrayList<double[][]> array_accu=null;
		double[][] accuracy=null;
		
		//����ÿ�����н��,���ÿ�����н��,����ƽ��׼ȷ����Ϣ�������ļ���
		input="./robustTrack2004/runId.txt";
		buffReader=new BufferedReader(new FileReader(input));
		while((tempLine=buffReader.readLine())!=null){
			runId=tempLine.split("\\.")[1];
			packageName="robustTrack2004/"+runId;
			array_accu=new ArrayList<double[][]>();//����array_accu����
			//ÿ��runId����5��heart_scale_test_x_regression�ļ�,��Ӧ�ز���5��׼ȷ����Ϣ
			for(int i=0;i<5;i++){
				fileName="heart_scale_test_"+i+"_regression";
				//���һ��heart_scale_test_x_regression�ļ�,����׼ȷ����Ϣ
				accuracy=compute_accuracy(fileName,packageName);
				array_accu.add(accuracy);
			}
			//��array_accu�е����ݴ���accu������,����accu[array_accu.size()]���ƽ��ֵ
			accu=new double[array_accu.size()+1][][];
			for(int i=0;i<array_accu.size();i++){
				accu[i]=array_accu.get(i);
			}
			
			/************************************/
			//����accu[accu.length-1]�����丳ֵ
			accu[accu.length-1]=new double[accu[0].length][accu[0][0].length];
			for(int i=0;i<accu.length-1;i++){
				for(int j=0;j<accu[i].length;j++){
					for(int k=0;k<accu[i][j].length;k++){
						//accu[accu.length-1][j][k]�д���ƽ��ֵ,������ǰ����1.0/(accu.length-1)
						accu[accu.length-1][j][k]+=accu[i][j][k]/(accu.length-1);
					}
				}
			}
			//��accu[accu.length-1]�����ļ���
			BufferedWriter buffWriter=null;
			input="./"+packageName+"/regression_accuracy";
			buffWriter=new BufferedWriter(new FileWriter(input));
			for(int i=0;i<accu[accu.length-1].length;i++){
				tempLine="";//��tempLineΪ���ַ���
				for(int j=0;j<accu[accu.length-1][i].length;j++){
					//tempLine=tempLine+accu[accu.length-1][i][j]+"\t";
					//����ƽ��׼ȷ��,����С�����3λ
					tempLine=tempLine+String.format("%.3f",accu[accu.length-1][i][j])+"\t";
				}
				//ȥ��tempLineĩβ��\t
				tempLine=tempLine.trim();
				buffWriter.write(tempLine+"\n");
			}
			buffWriter.close();
		}
		buffReader.close();
		System.out.println("��������heart_scale_test_x_regression�ļ���Ӧ��׼ȷ����Ϣ,�����..");
	}
	
	/**
	 * ���һ��heart_scale_test_x_regression_AP_value�ļ�,������������Ϣ(by Zoey)
	 * @throws IOException 
	 */
	public static double[][] compute_absolute_error(String fileName,String packageName) throws IOException{
		//��input�ļ�����predictedAP����
		BufferedReader buffReader=null;
		String input=null;
		String tempLine=null;
		String[] terms=null;
		double[] terms2=null;
		/**
		 * predictedAP����ĸ�ʽΪ:
		 * standard sD2 wIG sMV nQC c c2 c4
		 *     x     x   x   x   x  x  x  x
		 *     x     x   x   ...
		 * 
		 */
		double[][] predictedAP=null;
		ArrayList<double[]> array_predictedAP=new ArrayList<double[]>();
		
		input="./"+packageName+"/"+fileName;
		buffReader=new BufferedReader(new FileReader(input));
		while((tempLine=buffReader.readLine())!=null){
			terms=tempLine.split(" |\t");
			terms2=new double[terms.length];
			for(int i=0;i<terms.length;i++){
				terms2[i]=Double.parseDouble(terms[i]);
			}
			array_predictedAP.add(terms2);
		}
		buffReader.close();
		//��array_predictedAP�е����ݴ���predictedAP������
		predictedAP=new double[array_predictedAP.size()][];
		for(int i=0;i<array_predictedAP.size();i++){
			predictedAP[i]=array_predictedAP.get(i);
		}
		
		/**************************/
		//����ÿ��Ԥ�ⷽ����APԤ��������,������relativeError������,Ԥ�ⷽ������7��
		int labe_1=0;
		double labe_1_absolute_error=0.0;
		int labe_2=0;
		double labe_2_absolute_error=0.0;
		int labe_3=0;
		double labe_3_absolute_error=0.0;
		/**
		 * relativeError����ĸ�ʽΪ:<br>
		 *         sD2 wIG sMV nQC c c2 C4
		 * overall  x   x   x   x  x x  x
		 * hard     x   x   x   ...
		 * medium
		 * easy
		 * (hard+easy)
		 * 
		 */
		double[][] absoluteError=new double[5][7];
		
		//Ԥ���㷨����7��,k_methodΪԤ���㷨�ı��(��0��ʼ���)
		for(int k_method=0;k_method<7;k_method++){
			for(int i=0;i<predictedAP.length;i++){
				if(predictedAP[i][0]>0.4){
					labe_3++;
					labe_3_absolute_error+=Math.abs(predictedAP[i][1+k_method]-predictedAP[i][0]);
				}
				else if(predictedAP[i][0]>0.2&&predictedAP[i][0]<=0.4){
					labe_2++;
					labe_2_absolute_error+=Math.abs(predictedAP[i][1+k_method]-predictedAP[i][0]);
				}
				else{
					labe_1++;
					labe_1_absolute_error+=Math.abs(predictedAP[i][1+k_method]-predictedAP[i][0]);
				}
			}
			//����������
			absoluteError[0][k_method]=(labe_1_absolute_error+labe_2_absolute_error+labe_3_absolute_error)/(labe_1+labe_2+labe_3);
			absoluteError[1][k_method]=labe_1_absolute_error/labe_1;
			absoluteError[2][k_method]=labe_2_absolute_error/labe_2;
			absoluteError[3][k_method]=labe_3_absolute_error/labe_3;
			absoluteError[4][k_method]=(labe_1_absolute_error+labe_3_absolute_error)/(labe_1+labe_3);
			//����labe_x����
			labe_1=0;
			labe_2=0;
			labe_3=0;
			//����labe_x_relative_error����
			labe_1_absolute_error=0.0;
			labe_2_absolute_error=0.0;
			labe_3_absolute_error=0.0;
			
		}
		
		/**************************/
		//��relativeError�����е����ݴ����ļ���
		BufferedWriter buffWriter=null;
		
		input="./"+packageName+"/"+fileName+"_absolute_error";
		buffWriter=new BufferedWriter(new FileWriter(input));
		for(int i=0;i<absoluteError.length;i++){
			tempLine="";//����tempLine
			for(int j=0;j<absoluteError[i].length;j++){
				tempLine=tempLine+absoluteError[i][j]+"\t";
			}
			//ȥ��tempLineĩβ��\t
			tempLine=tempLine.trim();
			buffWriter.write(tempLine+"\n");
		}
		buffWriter.close();
		System.out.println("���һ��heart_scale_test_x_regression_AP_value�ļ�,������������Ϣ�������ļ�,�����..");
		
		//����relativeError����
		return absoluteError;
	}
	
	/**
	 * ��������heart_scale_test_x_regression_AP_value�ļ���Ӧ�ľ��������Ϣ��by Zoey��<br>
	 * @throws IOException 
	 * 
	 */
	public static void compute_absoluteError_batch() throws IOException{
		//��������׼ȷ���ļ�,������Ԥ�ⷽ���ڸ����н���ϵ�ƽ����������Ϣ
		BufferedReader buffReader=null;
		String tempLine=null;
		String input=null;
		String runId=null;
		String packageName=null;
		String fileName=null;
		double[][][] absolute_error=null;//���һ�����н����Ӧ��������
		ArrayList<double[][]> array_absolute_error=null;
		double[][] absoluteError=null;
		
		//����ÿ�����н��,���ÿ�����н��,����ƽ����������Ϣ�������ļ���
		input="./robustTrack2004/runId.txt";
		buffReader=new BufferedReader(new FileReader(input));
		while((tempLine=buffReader.readLine())!=null){
			runId=tempLine.split("\\.")[1];
			packageName="robustTrack2004/"+runId;
			array_absolute_error=new ArrayList<double[][]>();//����array_relative_error����
			//ÿ��runId����5��heart_scale_test_x_regression_AP_value�ļ�,��Ӧ�ز���5����������Ϣ
			for(int i=0;i<5;i++){
				fileName="heart_scale_test_"+i+"_regression_AP_value";
				//���һ��heart_scale_test_x_regression_AP_value�ļ�,������������Ϣ
				absoluteError=compute_absolute_error(fileName,packageName);
				array_absolute_error.add(absoluteError);
			}
			//��array_relative_error�е����ݴ���relative_error������,����relative_error[array_relative_error.size()]���ƽ��ֵ
			absolute_error=new double[array_absolute_error.size()+1][][];
			for(int i=0;i<array_absolute_error.size();i++){
				absolute_error[i]=array_absolute_error.get(i);
			}
			
			/************************************/
			//����relative_error[relative_error.length-1]�����丳ֵ
			absolute_error[absolute_error.length-1]=new double[absolute_error[0].length][absolute_error[0][0].length];
			for(int i=0;i<absolute_error.length-1;i++){
				for(int j=0;j<absolute_error[i].length;j++){
					for(int k=0;k<absolute_error[i][j].length;k++){
						//relative_error[relative.length-1][j][k]�д���ƽ��ֵ,������ǰ����1.0/(relative_error.length-1)
						absolute_error[absolute_error.length-1][j][k]+=absolute_error[i][j][k]/(absolute_error.length-1);
					}
				}
			}
			//��relative_error[relative_error.length-1]�����ļ���
			BufferedWriter buffWriter=null;
			input="./"+packageName+"/regression_absoluteError";
			buffWriter=new BufferedWriter(new FileWriter(input));
			for(int i=0;i<absolute_error[absolute_error.length-1].length;i++){
				tempLine="";//��tempLineΪ���ַ���
				for(int j=0;j<absolute_error[absolute_error.length-1][i].length;j++){
					//tempLine=tempLine+relative_error[accu.length-1][i][j]+"\t";
					//����ƽ�����,����С�����3λ
					tempLine=tempLine+String.format("%.3f",absolute_error[absolute_error.length-1][i][j])+"\t";
				}
				//ȥ��tempLineĩβ��\t
				tempLine=tempLine.trim();
				buffWriter.write(tempLine+"\n");
			}
			buffWriter.close();
		}
		buffReader.close();
		System.out.println("��������heart_scale_test_x_regression_AP_value�ļ���Ӧ�ľ��������Ϣ,�����..");
	}
	
	/**
	 * �������Իع��ļ�,��heart_scale_test_x�ļ�,������Ӧ��Ԥ������ļ���<br>
	 * @throws IOException 
	 * 
	 */
	public static void task_control() throws IOException{
		//�����Իع�Ľ�������ڴ�
		String input=null;
		input="./robustTrack2004/�½��ı��ĵ� (3).txt";
		ArrayRun=load_linear(input);
		
		//��������ÿ��heart_scale_test_x�ļ���Ӧ��Ԥ�����,�������ļ�
		generate_prediction_batch();
		
		//��������heart_scale_test_x_regression�ļ���Ӧ��׼ȷ����Ϣ
		compute_accuracy_batch();
		
		//��������heart_scale_test_x_regression_AP_value�ļ���Ӧ�ľ��������Ϣ
		compute_absoluteError_batch();		
		
		
	}
	
	
	

}

class Run_linear{
	String runId;
	Heart_train[] heart_train;
	
	public Run_linear(ListIterator<String> iter){
		String tempLine=null;
		tempLine=iter.next();
		runId=tempLine.replaceFirst("[\\d]+��packageName=robustTrack2004/", "").trim();
		heart_train=new Heart_train[5];
		
		//Ϊheart_train���鸳ֵ
		for(int i=0;i<5;i++){
			iter.next();
			heart_train[i]=new Heart_train(iter);
		}
	}

}

class Heart_train{
	double[] sD2;
	double[] wIG;
	double[] sMV;
	double[] nQC;
	double[] c;
	double[] c2;
	double[] c4;
	
	public Heart_train(ListIterator<String> iter){
		sD2=new double[2];
		wIG=new double[2];
		sMV=new double[2];
		nQC=new double[2];
		c=new double[2];
		c2=new double[2];
		c4=new double[2];
		//ʹ��iter�е���Ϣ�����鸳ֵ
		String tempLine=null;
		String[] terms=null;
		
		tempLine=iter.next();
		terms=tempLine.split(" |\t");
		sD2[0]=Double.parseDouble(terms[1].split("=")[1]);
		sD2[1]=Double.parseDouble(terms[2].split("=")[1]);
		tempLine=iter.next();
		terms=tempLine.split(" |\t");
		wIG[0]=Double.parseDouble(terms[1].split("=")[1]);
		wIG[1]=Double.parseDouble(terms[2].split("=")[1]);
		tempLine=iter.next();
		terms=tempLine.split(" |\t");
		sMV[0]=Double.parseDouble(terms[1].split("=")[1]);
		sMV[1]=Double.parseDouble(terms[2].split("=")[1]);
		tempLine=iter.next();
		terms=tempLine.split(" |\t");
		nQC[0]=Double.parseDouble(terms[1].split("=")[1]);
		nQC[1]=Double.parseDouble(terms[2].split("=")[1]);
		tempLine=iter.next();
		terms=tempLine.split(" |\t");
		c[0]=Double.parseDouble(terms[1].split("=")[1]);
		c[1]=Double.parseDouble(terms[2].split("=")[1]);
		tempLine=iter.next();
		terms=tempLine.split(" |\t");
		c2[0]=Double.parseDouble(terms[1].split("=")[1]);
		c2[1]=Double.parseDouble(terms[2].split("=")[1]);
		tempLine=iter.next();
		terms=tempLine.split(" |\t");
		c4[0]=Double.parseDouble(terms[1].split("=")[1]);
		c4[1]=Double.parseDouble(terms[2].split("=")[1]);
	}

}
