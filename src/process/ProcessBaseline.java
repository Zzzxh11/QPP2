package process;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * ��Ԥ��ֵΪNaN,��Ϊ0,������޸ĵ���ʾ<br>
 * ����baseline��׼ȷ��
 * @author 1
 *
 */
public class ProcessBaseline {

	/**
	 * ����SD2�Ľ��,����׼ȷ��
	 * @throws IOException 
	 */
	public static void computeAccuracy(String input_map,String input_sD2) throws IOException{
		//����input_sD2�ļ�
		FileReader fileReader=null;
		BufferedReader buffReader=null;
		String tempLine=null;
		String[] terms=null;
		ArrayList<SD2Result> array_sD2=new ArrayList<SD2Result>();
		SD2Result sD2Result=null;

		fileReader=new FileReader(input_sD2);
		buffReader=new BufferedReader(fileReader);
		while((tempLine=buffReader.readLine())!=null){
			sD2Result=new SD2Result(tempLine);
			array_sD2.add(sD2Result);
		}
		buffReader.close();
		//����SD2ֵ,��array_sD2�еĶ�������
		Collections.sort(array_sD2, new SD2Result_compare());
		//��array_sD2�еĶ���classLabelֵ
		double size=0;
		size=array_sD2.size();
		int size_1_4=0;
		int size_3_4=0;

		size_1_4=(int)(size/4-1);
		size_3_4=(int)(size/4*3-1);
		sD2Result=null;//sD2Result�ÿ�
		for(int i=0;i<array_sD2.size();i++){
			sD2Result=array_sD2.get(i);
			if(i<=size_1_4) sD2Result.setClassLabel("1");
			if(i>size_1_4&&i<=size_3_4) sD2Result.setClassLabel("2");
			if(i>size_3_4) sD2Result.setClassLabel("3");
		}
		//����topicֵ,��array_sD2�еĶ�������
		Collections.sort(array_sD2, new SD2Result_compare2());
		
		//array_info���ڴ洢input_map��array_sD2�е�classLabel��Ϣ
		ArrayList<StringBuffer> array_info=new ArrayList<StringBuffer>();
		StringBuffer info=null;
		int label_1=0;//input_map�ļ���classLabelΪlabel_1������
		int label_1_matched=0;//array_sD2��input_mapƥ�����Ϣ��label_1������
		int label_2=0;
		int label_2_matched=0;
		int label_3=0;
		int label_3_matched=0;

		//��ȡinput_map��classLabel��Ϣ
		fileReader=new FileReader(input_map);
		buffReader=new BufferedReader(fileReader);

		while((tempLine=buffReader.readLine())!=null){
			terms=tempLine.split(" |\t");
			info=new StringBuffer(terms[5]);
			array_info.add(info);
		}
		buffReader.close();
		//��ȡarray_sD2��classLabel��Ϣ
		int pointer=0;
		info=null;//info�ÿ�
		for(int i=0;i<array_sD2.size();i++){
			info=array_info.get(pointer++);
			info.append("\t"+array_sD2.get(i).getClassLabel());
		}
		
		//����׼ȷ��
		info=null;//info�ÿ�
		for(int i=0;i<array_info.size();i++){
			info=array_info.get(i);
			terms=info.toString().split(" |\t");
			if(terms[0].equalsIgnoreCase("1")){
				label_1++;
				if(terms[1].equalsIgnoreCase(terms[0])) label_1_matched++;
			}
			if(terms[0].equalsIgnoreCase("2")){
				label_2++;
				if(terms[1].equalsIgnoreCase(terms[0])) label_2_matched++;
			}
			if(terms[0].equalsIgnoreCase("3")){
				label_3++;
				if(terms[1].equalsIgnoreCase(terms[0])) label_3_matched++;
			}
		}
		//
		double accuracy_1=0;
		double accuracy_2=0;
		double accuracy_3=0;
		accuracy_1=(double)label_1_matched/label_1;
		accuracy_2=(double)label_2_matched/label_2;
		accuracy_3=(double)label_3_matched/label_3;
		//���������׼ȷ��
		double accuracy_4=0;
		accuracy_4=(double)(label_1_matched+label_2_matched+label_3_matched)/(label_1+label_2+label_3);
		System.out.println("overall: accuracy="+accuracy_4);
		System.out.println("hard: accuracy_1="+accuracy_1+"\nmedium: accuracy_2="+accuracy_2+"\neasy: accuracy_3="+accuracy_3);
		
		//����(hard+easy)׼ȷ��
		double accuracy_5=0;
		accuracy_5=(double)(label_1_matched+label_3_matched)/(label_1+label_3);
		System.out.println("(hard+easy): accuracy_5="+accuracy_5);
	}
	/**
	 * ����SD2/WIG/SMV/NQC��׼ȷ��
	 * @throws IOException 
	 */
	public static void getAccuracy(String runId,String packageName) throws IOException{
		String input_map=null;
		String input_predict=null;
		//����SD2��׼ȷ��
		input_map="./"+packageName+"/map.normalized."+runId+"_classLabel";
		input_predict="./"+packageName+"/sD2Score."+runId;
		System.out.println("\nSD2��׼ȷ��:");
		computeAccuracy(input_map,input_predict);
		//����WIG��׼ȷ��
		input_predict="./"+packageName+"/wIGScore."+runId;
		System.out.println("\nWIG��׼ȷ��:");
		computeAccuracy(input_map,input_predict);
		//����SMV��׼ȷ��
		input_predict="./"+packageName+"/sMVScore."+runId;
		System.out.println("\nSMV��׼ȷ��:");
		computeAccuracy(input_map,input_predict);
		//����NQC��׼ȷ��
		input_predict="./"+packageName+"/nQCScore."+runId;
		System.out.println("\nNQC��׼ȷ��:");
		computeAccuracy(input_map,input_predict);
		
		//����C��׼ȷ��
		input_predict="./"+packageName+"/cScore."+runId;
		System.out.println("\nC��׼ȷ��:");
		computeAccuracy(input_map,input_predict);
		//����C2��׼ȷ��
		input_predict="./"+packageName+"/c2Score."+runId;
		System.out.println("\nC2��׼ȷ��:");
		computeAccuracy(input_map,input_predict);
		//����C4��׼ȷ��
		input_predict="./"+packageName+"/c4Score."+runId;
		System.out.println("\nC4��׼ȷ��:");
		computeAccuracy(input_map,input_predict);
		
		System.out.println("����SD2/WIG/SMV/NQC/C C2 C4��׼ȷ��,�����..");
	}
	/**
	 * 
	 * @param args
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException{
		//����SD2�Ľ��,����׼ȷ��
		String input_map="./robustTrack2004/map.normalized.apl04rsTw_classLabel";
		String input_sD2="./robustTrack2004/sD2Score.apl04rsTw";
		computeAccuracy(input_map,input_sD2);
		
	}

}

class SD2Result{
	private int topic;
	private double sD2;
	private String classLabel;

	public int getTopic() {
		return topic;
	}
	public void setTopic(int topic) {
		this.topic = topic;
	}
	public double getsD2() {
		return sD2;
	}
	public void setsD2(double sD2) {
		this.sD2 = sD2;
	}
	public String getClassLabel() {
		return classLabel;
	}
	public void setClassLabel(String classLabel) {
		this.classLabel = classLabel;
	}
	/**
	 * ��sD2ΪNaN,��Ϊ0,������޸ĵ���ʾ
	 * @param tempLine
	 */
	public SD2Result(String tempLine){
		String[] terms=null;
		terms=tempLine.split(" |\t");
		topic=Integer.parseInt(terms[1]);
		sD2=Double.parseDouble(terms[3]);
		classLabel=null;
		
		//��sD2ΪNaN,��Ϊ0
		if(new Double(sD2).isNaN()){
			sD2=0;
			System.out.println("\n\n������¼�д���NaNֵ,�ڴ����ѱ���Ϊ0��tempLine="+tempLine);
		}
	}
}

/**
 * ��SD2Result��Ķ����������,����SD2ֵ��С��������
 *
 */
class SD2Result_compare implements Comparator<SD2Result>{

	/**
	 * ����SD2ֵ��С��������
	 */
	@Override
	public int compare(SD2Result arg0, SD2Result arg1) {
		if(arg0.getsD2()<arg1.getsD2())
			return -1;
		if(arg0.getsD2()>arg1.getsD2())
			return 1;
		return 0;
	}
}
/**
 * ��SD2Result��Ķ����������,����topicֵ��С��������
 *
 */
class SD2Result_compare2 implements Comparator<SD2Result>{

	/**
	 * ����topicֵ��С��������
	 */
	@Override
	public int compare(SD2Result arg0, SD2Result arg1) {
		if(arg0.getTopic()<arg1.getTopic())
			return -1;
		if(arg0.getTopic()>arg1.getTopic())
			return 1;
		return 0;
	}
}
