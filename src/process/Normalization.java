package process;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import utils.Result;

public class Normalization {

	/**
	 * ����input�ļ�,������1��2��������,�����޸ġ�
	 * 1��ʹ����������0,2��ʹ������[0,1]֮�䡣
	 * @param input
	 * @param output
	 * @throws IOException 
	 */
	public static void normalize_input(String input) throws IOException{
		FileReader fileReader=null;
		BufferedReader buffReader=null;
		String tempLine=null;
		//
		double max=0;
		double min=0;
		double score=0;
		int pointer=0;
		ArrayList<Result> array_result=new ArrayList<Result>();
		Result result=null;
		fileReader=new FileReader(input);
		buffReader=new BufferedReader(fileReader);
		while((tempLine=buffReader.readLine())!=null){
			//����Ϣ����array_result��
			result=new Result(tempLine);
			array_result.add(result);
			score=result.getScore();
			//��min,max����ֵ
			if(pointer==0){
				pointer++;
				min=score;
				max=score;
			}
			//��ȡ�ļ���min,max
			if(score<min) min=score;
			if(score>max) max=score;
		}
		buffReader.close();
		//��min<0,array_result�����ж���ķ�����ȥmin
		if(min<0){
			result=null;//result�ÿ�
			for(int i=0;i<array_result.size();i++){
				result=array_result.get(i);
				result.score=result.score-min;
			}
			//����maxֵ����ʹarray_result�еķ���������[0,1]��
			max=max-min;
			if(max>1){
				result=null;
				for(int i=0;i<array_result.size();i++){
					result=array_result.get(i);
					result.score=result.score/max;
				}
			}
		}else{
			//��min>=0ʱ������max
			if(max>1){
				result=null;
				for(int i=0;i<array_result.size();i++){
					result=array_result.get(i);
					result.score=result.score/max;
				}
			}
		}
		//��array_result�е���Ϣ����output�С�
		FileWriter fileWriter=null;
		BufferedWriter buffWriter=null;
		fileWriter=new FileWriter(input+"_normalized");
		buffWriter=new BufferedWriter(fileWriter);
		result=null;
		for(int i=0;i<array_result.size();i++){
			result=array_result.get(i);
			buffWriter.write(result.getTempLine());
		}
		buffWriter.close();
		
		//ʹ��input+"_normalized"�ļ��滻input�ļ�
		File file1=null;
		File file2=null;
		file1=new File(input);
		file1.delete();
		file2=new File(input+"_normalized");
		file2.renameTo(file1);
		System.out.println("�淶��input�ļ�,�����..");
	}
	/**
	 * ɾ��input�ļ���topics��Ӧ�ļ�¼
	 * @param input
	 * @param topics
	 * @throws IOException 
	 */
	public static void delete_topic(String input,String topics) throws IOException{
		FileReader fileReader=null;
		BufferedReader buffReader=null;
		String tempLine=null;
		String[] terms=null;
		ArrayList<Result> array_result=new ArrayList<Result>();
		Result result=null;
		
		//��topics�е���Ϣ��ʽΪ"topic1\ttopic2"��
		String[] arrayTopic=topics.split(" |\t");
		boolean need_delete=false;
		fileReader=new FileReader(input);
		buffReader=new BufferedReader(fileReader);
		//input�ļ�����Ϣ��ɾ��������Ϣ�󣬴���array_result�С�
		while((tempLine=buffReader.readLine())!=null){
			terms=tempLine.split(" |\t");
			//����nee_deletedΪfalse
			need_delete=false;
			for(int i=0;i<arrayTopic.length;i++){
				if(terms[0].equalsIgnoreCase(arrayTopic[i])){
					need_delete=true;
					break;
				}
			}
			//��need_deleteΪfalse,��tempLine����array_result�С�
			if(need_delete==false){
				result=new Result(tempLine);
				array_result.add(result);
			}
		}
		buffReader.close();
		//��array_result�е���Ϣ����output�ļ���
		FileWriter fileWriter=null;
		BufferedWriter buffWriter=null;
		fileWriter=new FileWriter(input+"_part_deleted");
		buffWriter=new BufferedWriter(fileWriter);
		result=null;//result�ÿ�
		for(int i=0;i<array_result.size();i++){
			result=array_result.get(i);
			buffWriter.write(result.getTempLine());
		}
		buffWriter.close();
		//
		//ʹ��input+"_part_deleted"�ļ��滻input�ļ�
		File file1=null;
		File file2=null;
		file1=new File(input);
		file1.delete();
		file2=new File(input+"_part_deleted");
		file2.renameTo(file1);
		System.out.println("ɾ��input�ļ���topics��Ӧ�ļ�¼,�����..");
	}


	/**
	 * 
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException{
		String parent_root="C:/Users/1/Desktop/�½��ļ���"+"/";
		File file=null;
		File[] list=null;
		String input=null;
		//��ɾ����topicΪ672
		String topics="95\t100";
		file=new File(parent_root);
		list=file.listFiles();
		System.out.println("��Ŀ¼�����ļ��ĸ���Ϊ:"+list.length);
		//�����Щ�ļ�����
		for(int i=0;i<list.length;i++){
			System.out.println(list[i].getName());
		}
		System.out.println();
		//
		for(int i=0;i<list.length;i++){
			if(list[i].getName().contains("input.")){
				input=parent_root+list[i].getName();
				//�淶���ļ����е�input�ļ�
				normalize_input(input);
				//ɾ��input�ļ���topics��Ӧ�ļ�¼
				//delete_topic(input,topics);
			}
		}
		System.out.println("�淶���ļ����е�input�ļ�,��ɾ��input�ļ���topics��Ӧ�ļ�¼,�����..");
	}

}
