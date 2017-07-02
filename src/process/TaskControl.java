package process;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class TaskControl {
	
	/**
	 * 
	 * @param args
	 * @throws IOException 
	 * @throws InterruptedException 
	 */
	public static void main(String[] args) throws IOException, InterruptedException{
		
		//��ÿ���ļ���Ϊ��λ,����5��heart_scale_train_x,heart_scale_test_x�ļ�
		//by Zoey
		//generate_heart_scale_batch();
		
		//by Zoey
		generate_accuracy_batch();
		/*
		//Ϊÿ����֯������Ӧ��׼ȷ����Ϣ
		generate_accuracy_batch();
		*/
		//
		//
		/*
		//����heart_scale.runId�ļ�
		String runId=null;
		String packageName=null;
		FileReader fileReader=null;
		BufferedReader buffReader=null;
		String tempLine=null;
		String runIdFile=null;
		
		runIdFile="runId.txt";
		packageName="robustTrack2004/apl04rsDw";
		fileReader=new FileReader("./"+packageName+"/"+runIdFile);
		buffReader=new BufferedReader(fileReader);
		
		while((tempLine=buffReader.readLine())!=null){
			runId=tempLine.split("\\.")[1];
			System.out.println("\n��ʼ����heart_scale."+runId);
			ProcessSomething.getHeart_scale(runId, packageName);
			Thread.sleep(2000);
		}
		buffReader.close();
		System.out.println("����heart_scale.runId�ļ�,�����..");
		//����heart_scale_train,heart_scale_test�ļ�
		ProcessSomething.combineData_2(packageName, runIdFile);
		//�鿴heart_scale_train,heart_scale_test�ļ�,������NaN,��ɾ����һ������ֵ
		String fileName="heart_scale_train";
		ProcessSomething.delete_NaN(packageName, fileName);
		fileName="heart_scale_test";
		ProcessSomething.delete_NaN(packageName, fileName);
		*/
		/*
		String packageName="robustTrack2004/pircRB04d2";
		String runId="pircRB04td3";
		//����hard,medium,easy�����׼ȷ��
		System.out.println("֧����������׼ȷ��:");
		String input_test="./"+packageName+"/heart_scale_test";
		String input_predict="./"+packageName+"/heart_scale_test.predict";
		ProcessSomething.computeAccuracy(input_test,input_predict);
		
		//baseline: ����SD2/WIG/SMV/NQC��׼ȷ��
		ProcessBaseline.getAccuracy(packageName, runId);
		*/
		
	}
	
	/**
	 * �˷�����generate_heart_scale_batch()��������<br>
	 * 1������heart_scale�ļ�
	 * 2���鿴heart_scale�ļ�,������NaN,��ɾ����һ������ֵ
	 * 3������heart_scale_train_x,heart_scale_test_x�ļ�
	 * @throws IOException 
	 * @throws InterruptedException 
	 */
	public static void generate_heart_scale(String runId,String packageName) throws IOException, InterruptedException{
		//����heart_scale�ļ�
		ProcessSomething.getHeart_scale(runId, packageName);
		System.out.println("����heart_scale�ļ�,�����..");
		
		//�鿴heart_scale�ļ�,������NaN,��ɾ����һ������ֵ
		//
		//��ʱ�Ķ�: ��NaN�滻Ϊ0
		String fileName="heart_scale";
		ProcessSomething.delete_NaN(packageName, fileName);
		
		//��ʱ����: 
		//��packageName�ļ�����,��AP��Ϣ����heart_scale�ļ���,�γ�heart2�ļ���
		//
		time_20170529.Process_baseline_2.add_ap_to_heart_scale(runId, "heart2", packageName);
		
		//��ʱ����:
		//ȥ����heart2�ļ��е�<index:>��Ϣ
	
		time_20170529.Process_baseline_2.remove_index(packageName);
		
		
		//����heart_scale_train_x,heart_scale_test_x�ļ�
		ProcessSomething.combineData_2(packageName);
		
	}
	/**
	 * ����robustTrack2004�µĸ����ļ���,��ÿ���ļ���Ϊ��λ,����5��heart_scale_train_x,heart_scale_test_x�ļ�
	 * @throws InterruptedException 
	 * @throws IOException 
	 */
	public static void generate_heart_scale_batch() throws IOException, InterruptedException{
		String packageName=null;
		String runId=null;
		File file=null;
		File[] file_list=null;
		//by zoey
		//file=new File("./robustTrack2004");
		//(by Zoey)
		file=new File("./robustTrack2004_2features2");
		
		file_list=file.listFiles();
		for(int i=0;i<file_list.length;i++){
			//Ϊÿ�����ļ��в���heart_scale_train_x,heart_scale_test_x�ļ�
			packageName="robustTrack2004_2features2/"+file_list[i].getName();
			runId=file_list[i].getName();
			System.out.println("\n��ʼΪ"+packageName+"���ļ��в���5��heart_scale_train_x,heart_scale_test_x�ļ�..");
			generate_heart_scale(runId,packageName);
			Thread.sleep(2000);
		}
		System.out.println("��ÿ���ļ���Ϊ��λ,����5��heart_scale_train_x,heart_scale_test_x�ļ�,�����..");
	}
	/**
	 * ����һ����֯��Ӧ��׼ȷ����Ϣ
	 * @throws IOException 
	 * 
	 */
	public static void generate_accuracy(String runId,String packageName) throws IOException{
		//���org
		System.out.println("0��Organization: "+packageName.split("/")[1]);
		//����hard,medium,easy�����׼ȷ��
		System.out.println("֧����������׼ȷ��:");
		
		String input_test=null;
		String input_predict=null;
		//����heart_scale_test_0��׼ȷ��
		double[] accu_0=null;
		input_test="./"+packageName+"/heart_scale_test_0";
		input_predict="./"+packageName+"/heart_scale_test_0.predict";
		accu_0=ProcessSomething.computeAccuracy(input_test,input_predict);
		//����heart_scale_test_1��׼ȷ��
		double[] accu_1=null;
		input_test="./"+packageName+"/heart_scale_test_1";
		input_predict="./"+packageName+"/heart_scale_test_1.predict";
		accu_1=ProcessSomething.computeAccuracy(input_test,input_predict);
		//����heart_scale_test_2��׼ȷ��
		double[] accu_2=null;
		input_test="./"+packageName+"/heart_scale_test_2";
		input_predict="./"+packageName+"/heart_scale_test_2.predict";
		accu_2=ProcessSomething.computeAccuracy(input_test,input_predict);
		//����heart_scale_test_3��׼ȷ��
		double[] accu_3=null;
		input_test="./"+packageName+"/heart_scale_test_3";
		input_predict="./"+packageName+"/heart_scale_test_3.predict";
		accu_3=ProcessSomething.computeAccuracy(input_test,input_predict);
		//����heart_scale_test_4��׼ȷ��
		double[] accu_4=null;
		input_test="./"+packageName+"/heart_scale_test_4";
		input_predict="./"+packageName+"/heart_scale_test_4.predict";
		accu_4=ProcessSomething.computeAccuracy(input_test,input_predict);
		
		//����5���µ�ƽ��׼ȷ��
		double[] accu_5=null;//�洢5���µ�ƽ��׼ȷ��
		accu_5=new double[5];
		for(int i=0;i<5;i++){
			accu_5[i]=(accu_0[i]+accu_1[i]+accu_2[i]+accu_3[i]+accu_4[i])/5;
		}
		System.out.println("overall: accuracy="+accu_5[0]+"\nhard: accuracy="+accu_5[1]+"\nmedium: accuracy="+accu_5[2]+
							"\neasy: accuracy="+accu_5[3]+"\n(hard+easy): accuracy="+accu_5[4]);
		
		//baseline: ����SD2/WIG/SMV/NQC C C2 C4��׼ȷ��
		//by Zoey
		//ProcessBaseline.getAccuracy(runId,packageName);
	}
	/**
	 * @throws IOException 
	 * 
	 */
	public static void generate_accuracy_batch() throws IOException{
		String packageName=null;
		String runId=null;
		//by Zoey
		//packageName="robustTrack2004/"+"apl04rsTDNfw";
		packageName="robustTrack2004_2features2/"+"apl04rsTDNfw";
		runId="apl04rsTDNfw";
		generate_accuracy(runId,packageName);
		//by Zoey
		//packageName="robustTrack2004/"+"fub04TDNge";
		packageName="robustTrack2004_2features2/"+"fub04TDNge";
		runId="fub04TDNge";
		generate_accuracy(runId,packageName);
		//
		//packageName="robustTrack2004/"+"humR04t5e1";
		packageName="robustTrack2004_2features2/"+"humR04t5e1";
		runId="humR04t5e1";
		generate_accuracy(runId,packageName);
		//
		//packageName="robustTrack2004/"+"icl04pos2f";
		packageName="robustTrack2004_2features2/"+"icl04pos2f";
		runId="icl04pos2f";
		generate_accuracy(runId,packageName);
		//
		//packageName="robustTrack2004/"+"JuruTitDes";
		packageName="robustTrack2004_2features2/"+"JuruTitDes";
		runId="JuruTitDes";
		generate_accuracy(runId,packageName);
		//
		//packageName="robustTrack2004/"+"mpi04r07";
		packageName="robustTrack2004_2features2/"+"mpi04r07";
		runId="mpi04r07";
		generate_accuracy(runId,packageName);
		//
		//packageName="robustTrack2004/"+"NLPR04NcA";
		packageName="robustTrack2004_2features2/"+"NLPR04NcA";
		runId="NLPR04NcA";
		generate_accuracy(runId,packageName);
		//
		//packageName="robustTrack2004/"+"pircRB04td2";
		packageName="robustTrack2004_2features2/"+"pircRB04td2";
		runId="pircRB04td2";
		generate_accuracy(runId,packageName);
		//
		//packageName="robustTrack2004/"+"polyudp5";
		packageName="robustTrack2004_2features2/"+"polyudp5";
		runId="polyudp5";
		generate_accuracy(runId,packageName);
		//
		//packageName="robustTrack2004/"+"SABIR04BA";
		packageName="robustTrack2004_2features2/"+"SABIR04BA";
		runId="SABIR04BA";
		generate_accuracy(runId,packageName);
		//
		//packageName="robustTrack2004/"+"uogRobLWR10";
		packageName="robustTrack2004_2features2/"+"uogRobLWR10";
		runId="uogRobLWR10";
		generate_accuracy(runId,packageName);
		//
		//packageName="robustTrack2004/"+"vtumlong436";
		packageName="robustTrack2004_2features2/"+"vtumlong436";
		runId="vtumlong436";
		generate_accuracy(runId,packageName);
		//
		//packageName="robustTrack2004/"+"wdoqla1";
		packageName="robustTrack2004_2features2/"+"wdoqla1";
		runId="wdoqla1";
		generate_accuracy(runId,packageName);
		
	}

}
