package process;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;

import utils.Result;
import utils.Result_compare;

public class ProcessTrivial {

	/**
	 * �ؽ�input�ļ���<br>
	 * 1����input�ļ��е��ĵ���������: sort by topic, then by score, and
	 * then by docno, which is the traditional sort order for TREC runs
	 * 2������rank��Ϣ,rankֵΪ1,2,3,...,n��
	 * 3��topic_sumΪ��input�ļ���Ӧ�е�topic����������input�ļ��е�ʵ��topic����
	 * ��topic_sum����ͬ�������ʾ��Ϣ����ֹ����<br><br>
	 * ���������input�ļ���topic������topic_sum��Ϊ-1��
	 * @param input
	 * @throws IOException 
	 */
	public static void rebuildInput(String input,int topic_sum) throws IOException{
		FileReader fileReader=null;
		BufferedReader buffReader=null;
		FileWriter fileWriter=null;
		BufferedWriter buffWriter=null;
		String tempLine=null;
		ArrayList<Result> array_result=new ArrayList<Result>();
		Result result=null;

		fileReader=new FileReader(input);
		buffReader=new BufferedReader(fileReader);
		//��tempLine����Ϣ����array_result��
		while((tempLine=buffReader.readLine())!=null){
			result=new Result(tempLine);
			array_result.add(result);
		}
		buffReader.close();
		//��array_result��Ϣ��������
		Collections.sort(array_result, new Result_compare());
		//����array_result�ж���result��rank��Ϣ,rankֵΪ1,2,3,...,n
		int rank=1;
		int preTopic=0;
		for(int i=0;i<array_result.size();i++){
			result=array_result.get(i);
			//��preTopic==0,��preTopic��ֵ
			if(preTopic==0) preTopic=result.getTopic();
			//��preTopic��result.getTopic()��ͬ,����result��rank��Ϣ,Ȼ��rank++
			if(preTopic==result.getTopic()){
				result.setRank(rank);
				rank++;
			}
			//��preTopic��result.getTopic()����ͬ,����rank,preTopic,����result��rank��Ϣ,rank++
			if(preTopic!=result.getTopic()){
				rank=1;
				preTopic=result.getTopic();
				result.setRank(rank);
				rank++;
			}
		}
		//���array_result��Ϣ
		fileWriter=new FileWriter(input);
		buffWriter=new BufferedWriter(fileWriter);
		for(int i=0;i<array_result.size();i++){
			result=array_result.get(i);
			buffWriter.write(result.getTempLine());
		}
		buffWriter.close();
		System.out.println("��input�ļ��е��ĵ��������򲢸���rank��Ϣ,�����..");
		//��topic_sum��Ϊ-1,���array_result�е�topic����
		int real_topic_sum=0;
		result=null;//result�ÿ�
		preTopic=0;//preTopic�ÿ�
		if(topic_sum!=-1){
			for(int i=0;i<array_result.size();i++){
				result=array_result.get(i);
				//��preTopic==0,��preTopic����ֵ,real_topic_sum++��
				if(preTopic==0){
					preTopic=result.getTopic();
					real_topic_sum++;
				}
				//��preTopic==result.getTopic(),������һ��ѭ��
				if(preTopic==result.getTopic()){
					continue;
				}
				//��preTopic!=result.getTopic(),����preTopic,real_topic_sum++��
				if(preTopic!=result.getTopic()){
					preTopic=result.getTopic();
					real_topic_sum++;
				}
			}
			if(topic_sum!=real_topic_sum){
				System.out.println("\n\ntopic_sum��ʵ��topic���������!���˳�����..");
				System.exit(1);
			}
		}
	}


	/**
	 * ����ļ�����summary�ļ�������mapֵ
	 * @param input
	 * @throws IOException 
	 */
	public static void getFileName() throws IOException{
		File f = null;
		BufferedReader read = new BufferedReader(new InputStreamReader(System.in)); // ���ܿ���̨������
		System.out.println("������һ��Ŀ¼:"); // ��ʾ����Ŀ¼
		String path = read.readLine(); // ��ȡ·��
		f = new File(path); // �½��ļ�ʵ��
		File[] list = f.listFiles(); /* �˴���ȡ�ļ����µ������ļ� */
		System.out.println(list.length);
		// by ChenJiawei
		String fileName = null;
		FileReader fileReader = null;
		BufferedReader buffReader = null;
		String tempLine = null;
		//�洢�ļ���������summary�ļ���mapֵ
		ArrayList<Double> array_map=new ArrayList<Double>();
		double map=0;
		for (int i = 0; i < list.length; i++) {
			fileName = list[i].getName();
			
			//����summary�ļ�,��ȡmapֵ
			if(fileName.contains("summary.")){
				fileReader=new FileReader(list[i]);
				buffReader=new BufferedReader(fileReader);
				while((tempLine=buffReader.readLine())!=null){
					if(tempLine.contains("Queryid (Num):")&&tempLine.contains(" all")){
						for(int j=0;j<18;j++)
							tempLine=buffReader.readLine();
						map=Double.parseDouble(tempLine.trim());
						array_map.add(map);
						System.out.println(fileName+"\tmap="+map);
					}
				}
				buffReader.close();
			}
		}
		//�ҵ���һ��array_map������mapֵ
		map=0;
		for(int i=0;i<array_map.size();i++){
			if(map==0) map=array_map.get(i);
			if(map<array_map.get(i)) map=array_map.get(i);
		}
		System.out.println("����mapֵΪ: "+map);
		System.out.println("\n����ļ�����summary�ļ�������mapֵ,�����..");
	}
	/**
	 * ѭ������getFileName()�������������ͬ�ļ�����summary�ļ�������mapֵ��
	 * @throws IOException 
	 */
	public static void getFileName_batch() throws IOException{
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		String code="yes";
		while(code.equalsIgnoreCase("yes")){
			getFileName();
			System.out.println("�Ƿ����(yes or no):");
			code = reader.readLine();
		}
		System.out.println("ѭ������getFileName()����,�ѽ���..");
	}
	/**
	 * ѭ������rebuildInput(String input,int topic_sum)����,���ؽ�input�ļ���
	 * @throws IOException 
	 */
	public static void rebuildInput_batch() throws IOException{
		String path=null;
		int topic_sum=0;
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		System.out.println("�������ļ���·��:");
		path=reader.readLine();
		System.out.println("������topic_sumֵ(���������input�ļ���topic����,topic_sum��Ϊ-1��):");
		topic_sum=Integer.parseInt(reader.readLine().trim());
		//����path�ļ����µ��ļ�
		File file=null;
		File[] files=null;
		String input=null;
		file=new File(path);
		files=file.listFiles();
		for(int i=0;i<files.length;i++){
			if(files[i].getName().contains("input.")){
				input=files[i].getAbsolutePath();
				rebuildInput(input,topic_sum);
			}
		}
		System.out.println("ѭ������rebuildInput(String input,int topic_sum)����,�ѽ���..");
	}
	/**
	 * 
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException{
		/*
		//ѭ������getFileName()�������������ͬ�ļ�����summary�ļ�������mapֵ��
		getFileName_batch();
		*/
		//ѭ������rebuildInput(String input,int topic_sum)����,���ؽ�input�ļ���
		rebuildInput_batch();
	}

}
