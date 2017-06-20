package process;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.ListIterator;

import utils.Run_coeff;

public class VisualizeData {
	
	/**
	 * ����С�����3λ,ʹ������������
	 * @param input
	 * @throws IOException 
	 */
	public static void normalizeCoefficient(String input) throws IOException{
		FileReader fileReader=null;
		BufferedReader bufferedReader=null;
		FileWriter fileWriter=null;
		String tempLine=null;
		String[] terms=null;
		
		fileReader=new FileReader(input);
		bufferedReader=new BufferedReader(fileReader);
		fileWriter=new FileWriter(input+"_withCoefficientNormalized");
		while((tempLine=bufferedReader.readLine())!=null){
			if(tempLine.contains("=")){
				terms=tempLine.split("=");
				tempLine=terms[0]+"="+String.format("%.3f", Double.parseDouble(terms[1].trim()));
			}
			fileWriter.write(tempLine+"\n");
		}
		fileWriter.close();
		bufferedReader.close();
		System.out.println("��accuracy���й淶��,����С�����3λ,�����..");
	}
	/**
	 * ���� �½��ı��ĵ� (3).txt,����1/2*(hard+easy),����ӵ�easy����һ��
	 * @param input
	 * @throws IOException 
	 */
	public static void accuracy_mean_hard_easy(String input) throws IOException{
		FileReader fileReader=null;
		BufferedReader buffReader=null;
		ArrayList<StringBuffer> array_info=new ArrayList<StringBuffer>();
		StringBuffer info=null;
		String tempLine=null;
		
		fileReader=new FileReader(input);
		buffReader=new BufferedReader(fileReader);
		while((tempLine=buffReader.readLine())!=null){
			info=new StringBuffer(tempLine);
			array_info.add(info);
		}
		buffReader.close();
		//����array_info�е���Ϣ
		double accuracy_1=0;
		double accuracy_3=0;
		double accuracy_4=0;//hard��easy׼ȷ�ʵ�ƽ��ֵ
		info=null;//info�ÿ�
		for(int i=0;i<array_info.size();i++){
			info=array_info.get(i);
			tempLine=info.toString();
			if(tempLine.contains("hard: ")){
				accuracy_1=Double.parseDouble(tempLine.split("=")[1].trim());
			}
			if(tempLine.contains("easy: ")){
				accuracy_3=Double.parseDouble(tempLine.split("=")[1].trim());
				accuracy_4=(accuracy_1+accuracy_3)/2;
				info.append("\n1/2(hard+easy): accuracy_4="+accuracy_4);
			}
		}
		//ʹ��array_info�е���Ϣ����input�ļ�
		FileWriter fileWriter=null;
		BufferedWriter buffWriter=null;
		fileWriter=new FileWriter(input);
		buffWriter=new BufferedWriter(fileWriter);
		for(int i=0;i<array_info.size();i++){
			info=array_info.get(i);
			buffWriter.write(info.toString()+"\n");
		}
		buffWriter.close();
		System.out.println("��input�ļ���,���1/2(hard+easy),�����..");
	}
	/**
	 * �� �½��ı��ĵ� (3).txt_withCoefficientNormalized�ļ��е���Ϣ�Ա�����ʽ�����ļ��С�<br>
	 * @param input
	 * @throws IOException 
	 */
	public static void load_input(String input) throws IOException{
		ArrayList<Organization> array_org=new ArrayList<Organization>();
		Organization org=null;
		FileReader fileReader=null;
		BufferedReader buffReader=null;
		String tempLine=null;
		//input�ļ���,���˿�����,������Ϣ������array_info�����С�
		ArrayList<String> array_info=new ArrayList<String>();
		fileReader=new FileReader(input);
		buffReader=new BufferedReader(fileReader);
		while((tempLine=buffReader.readLine())!=null){
			if(!tempLine.trim().equalsIgnoreCase("")){
				array_info.add(tempLine);
			}
		}
		buffReader.close();
		//����array_info�е���Ϣ,����Ϣ����array_org��
		ListIterator<String> iter=array_info.listIterator();
		while(iter.hasNext()){
			tempLine=iter.next();
			if(tempLine.contains("Organization: ")){
				org=new Organization();
				org.name=tempLine.replaceFirst("[\\d]+��Organization: ", "").trim();
				tempLine=iter.next();
				if(tempLine.contains("֧����������׼ȷ��:")){
					org.support_svm=new Accuracy(iter);
				}
				tempLine=iter.next();
				if(tempLine.contains("SD2��׼ȷ��:")){
					org.sD2=new Accuracy(iter);
				}
				tempLine=iter.next();
				if(tempLine.contains("WIG��׼ȷ��:")){
					org.wIG=new Accuracy(iter);
				}
				tempLine=iter.next();
				if(tempLine.contains("SMV��׼ȷ��:")){
					org.sMV=new Accuracy(iter);
				}
				tempLine=iter.next();
				if(tempLine.contains("NQC��׼ȷ��:")){
					org.nQC=new Accuracy(iter);
				}
				//
				tempLine=iter.next();
				if(tempLine.contains("C��׼ȷ��:")){
					org.c=new Accuracy(iter);
				}
				tempLine=iter.next();
				if(tempLine.contains("C2��׼ȷ��:")){
					org.c2=new Accuracy(iter);
				}
				tempLine=iter.next();
				if(tempLine.contains("C4��׼ȷ��:")){
					org.c4=new Accuracy(iter);
				}
				//��org����array_org��
				array_org.add(org);
			}
		}
		//�Ա�����ʽ���array_org�е���Ϣ
		FileWriter fileWriter=null;
		BufferedWriter buffWriter=null;
		fileWriter=new FileWriter(input+"_visualize");
		buffWriter=new BufferedWriter(fileWriter);
		//���overall׼ȷ��
		tempLine="overall׼ȷ��:\nOrganization\tsupport_svm\tSD2\tWIG\tSMV\tNQC\tC\tC2\tC4\n";
		buffWriter.write(tempLine);
		for(int i=0;i<array_org.size();i++){
			org=array_org.get(i);
			tempLine=org.name+"\t"+org.support_svm.overall+"\t"+org.sD2.overall+"\t"+
			         org.wIG.overall+"\t"+org.sMV.overall+"\t"+org.nQC.overall+"\t"+
					 org.c.overall+"\t"+org.c2.overall+"\t"+org.c4.overall+"\n";
			buffWriter.write(tempLine);
		}
		//���hard׼ȷ��
		tempLine="\nhard׼ȷ��:\nOrganization\tsupport_svm\tSD2\tWIG\tSMV\tNQC\tC\tC2\tC4\n";
		buffWriter.write(tempLine);
		for(int i=0;i<array_org.size();i++){
			org=array_org.get(i);
			tempLine=org.name+"\t"+org.support_svm.hard+"\t"+org.sD2.hard+"\t"+
					org.wIG.hard+"\t"+org.sMV.hard+"\t"+org.nQC.hard+"\t"+
					org.c.hard+"\t"+org.c2.hard+"\t"+org.c4.hard+"\n";
			buffWriter.write(tempLine);
		}
		//���medium׼ȷ��
		tempLine="\nmedium׼ȷ��:\nOrganization\tsupport_svm\tSD2\tWIG\tSMV\tNQC\tC\tC2\tC4\n";
		buffWriter.write(tempLine);
		for(int i=0;i<array_org.size();i++){
			org=array_org.get(i);
			tempLine=org.name+"\t"+org.support_svm.medium+"\t"+org.sD2.medium+"\t"+
					org.wIG.medium+"\t"+org.sMV.medium+"\t"+org.nQC.medium+"\t"+
					org.c.medium+"\t"+org.c2.medium+"\t"+org.c4.medium+"\n";
			buffWriter.write(tempLine);
		}
		//���easy׼ȷ��
		tempLine="\neasy׼ȷ��:\nOrganization\tsupport_svm\tSD2\tWIG\tSMV\tNQC\tC\tC2\tC4\n";
		buffWriter.write(tempLine);
		for(int i=0;i<array_org.size();i++){
			org=array_org.get(i);
			tempLine=org.name+"\t"+org.support_svm.easy+"\t"+org.sD2.easy+"\t"+
					org.wIG.easy+"\t"+org.sMV.easy+"\t"+org.nQC.easy+"\t"+
					org.c.easy+"\t"+org.c2.easy+"\t"+org.c4.easy+"\n";
			buffWriter.write(tempLine);
		}
		//���(hard+easy)׼ȷ��
		tempLine="\n(hard+easy)׼ȷ��:\nOrganization\tsupport_svm\tSD2\tWIG\tSMV\tNQC\tC\tC2\tC4\n";
		buffWriter.write(tempLine);
		for(int i=0;i<array_org.size();i++){
			org=array_org.get(i);
			tempLine=org.name+"\t"+org.support_svm.mean+"\t"+org.sD2.mean+"\t"+
					org.wIG.mean+"\t"+org.sMV.mean+"\t"+org.nQC.mean+"\t"+
					org.c.mean+"\t"+org.c2.mean+"\t"+org.c4.mean+"\n";
			buffWriter.write(tempLine);
		}
		buffWriter.close();
		System.out.println("��input�ļ��е���Ϣ�Ա�����ʽ�����ļ���,�����..");
	}
	/**
	 * ���ӻ�ϵͳ��pearson/kendall/spearmanϵ��
	 * @param input
	 * @throws IOException 
	 */
	public static void visualize_run_coeff(String input) throws IOException{
		FileReader fileReader=null;
		BufferedReader buffReader=null;
		String tempLine=null;
		ArrayList<Run_coeff> array_run=new ArrayList<Run_coeff>();
		Run_coeff run=null;
		FileWriter fileWriter=null;
		BufferedWriter buffWriter=null;
		
		fileReader=new FileReader(input);
		buffReader=new BufferedReader(fileReader);
		
		//��ȡpearson,kendall,spearmanϵ���ļ�,����array_run��
		while((tempLine=buffReader.readLine())!=null){
			run=new Run_coeff();
			//ϵ���ļ���,һ��ϵͳռ��24��,���ǳ�������24����Ϊѭ���塣
			run.runId=tempLine.replaceFirst("[\\d]+��trackΪ", "");
			buffReader.readLine();
			//��ȡpearsonϵ��
			tempLine=buffReader.readLine();
			run.p_sD2=tempLine.split("=")[1];
			tempLine=buffReader.readLine();
			run.p_wIG=tempLine.split("=")[1];
			tempLine=buffReader.readLine();
			run.p_sMV=tempLine.split("=")[1];
			tempLine=buffReader.readLine();
			run.p_nQC=tempLine.split("=")[1];
			tempLine=buffReader.readLine();
			run.p_c=tempLine.split("=")[1];
			tempLine=buffReader.readLine();
			run.p_c2=tempLine.split("=")[1];
			tempLine=buffReader.readLine();
			run.p_c3=tempLine.split("=")[1];
			tempLine=buffReader.readLine();
			run.p_c4=tempLine.split("=")[1];
			//��ȡkendallϵ��
			tempLine=buffReader.readLine();
			run.k_sD2=tempLine.split("=")[1];
			tempLine=buffReader.readLine();
			run.k_wIG=tempLine.split("=")[1];
			tempLine=buffReader.readLine();
			run.k_sMV=tempLine.split("=")[1];
			tempLine=buffReader.readLine();
			run.k_nQC=tempLine.split("=")[1];
			tempLine=buffReader.readLine();
			run.k_c=tempLine.split("=")[1];
			tempLine=buffReader.readLine();
			run.k_c2=tempLine.split("=")[1];
			tempLine=buffReader.readLine();
			run.k_c3=tempLine.split("=")[1];
			tempLine=buffReader.readLine();
			run.k_c4=tempLine.split("=")[1];
			//��ȡspearmanϵ��
			tempLine=buffReader.readLine();
			run.s_sD2=tempLine.split("=")[1];
			tempLine=buffReader.readLine();
			run.s_wIG=tempLine.split("=")[1];
			tempLine=buffReader.readLine();
			run.s_sMV=tempLine.split("=")[1];
			tempLine=buffReader.readLine();
			run.s_nQC=tempLine.split("=")[1];
			tempLine=buffReader.readLine();
			run.s_c=tempLine.split("=")[1];
			tempLine=buffReader.readLine();
			run.s_c2=tempLine.split("=")[1];
			tempLine=buffReader.readLine();
			run.s_c3=tempLine.split("=")[1];
			tempLine=buffReader.readLine();
			run.s_c4=tempLine.split("=")[1];
			//�����п���
			buffReader.readLine();
			buffReader.readLine();
			
			//��run����array_run��
			array_run.add(run);
		}
		//�ر�bufferedReader
		buffReader.close();
		//��array_run�е�run����д���ļ���
		fileWriter=new FileWriter(input+"_table");
		buffWriter=new BufferedWriter(fileWriter);
		//��Ԥ��ֵ��AP��pearsonϵ��д���ļ�
		tempLine="Ԥ��ֵ��AP��pearsonϵ��:\n";
		//tempLine=tempLine+"runId\tWIG\tSD\tSMV\tNQC\tIA_SUM\tSDMulti\tSDMultiWIG_IASUM\tCF\tCF_IASUM\n";
		tempLine=tempLine+"runId\tSD2\tWIG\tSMV\tNQC\tC\tC2\tC3\tC4\n";
		
		buffWriter.write(tempLine);
		for(int i=0;i<array_run.size();i++){
			run=array_run.get(i);
			//tempLine=run.runId+"\t"+run.p_wIG+"\t"+run.p_sD+"\t"+run.p_sMV+"\t"+run.p_nQC+"\t"+run.p_iA_SUM+"\t"+run.p_sDMultiWIG+"\t"+run.p_sDMultiWIG_IASUM+"\t"+run.p_cF+"\t"+run.p_cF_IASUM+"\n";
			tempLine=run.runId+"\t"+run.p_sD2+"\t"+run.p_wIG+"\t"+run.p_sMV+"\t"+run.p_nQC+"\t"+run.p_c+"\t"+run.p_c2+"\t"+run.p_c3+"\t"+run.p_c4+"\n";
			buffWriter.write(tempLine);
		}
		//��Ԥ��ֵ��AP��kendallϵ��д���ļ�
		tempLine="\n\nԤ��ֵ��AP��kendallϵ��:\n";
		//tempLine=tempLine+"runId\tWIG\tSD\tSMV\tNQC\tIA_SUM\tSDMulti\tSDMultiWIG_IASUM\tCF\tCF_IASUM\n";
		tempLine=tempLine+"runId\tSD2\tWIG\tSMV\tNQC\tC\tC2\tC3\tC4\n";
		
		buffWriter.write(tempLine);
		for(int i=0;i<array_run.size();i++){
			run=array_run.get(i);
			//tempLine=run.runId+"\t"+run.k_wIG+"\t"+run.k_sD+"\t"+run.k_sMV+"\t"+run.k_nQC+"\t"+run.k_iA_SUM+"\t"+run.k_sDMultiWIG+"\t"+run.k_sDMultiWIG_IASUM+"\t"+run.k_cF+"\t"+run.k_cF_IASUM+"\n";
			tempLine=run.runId+"\t"+run.k_sD2+"\t"+run.k_wIG+"\t"+run.k_sMV+"\t"+run.k_nQC+"\t"+run.k_c+"\t"+run.k_c2+"\t"+run.k_c3+"\t"+run.k_c4+"\n";
			buffWriter.write(tempLine);
		}
		//��Ԥ��ֵ��AP��spearmanϵ��д���ļ�
		tempLine="\n\nԤ��ֵ��AP��spearmanϵ��:\n";
		//tempLine=tempLine+"runId\tWIG\tSD\tSMV\tNQC\tIA_SUM\tSDMulti\tSDMultiWIG_IASUM\tCF\tCF_IASUM\n";
		tempLine=tempLine+"runId\tSD2\tWIG\tSMV\tNQC\tC\tC2\tC3\tC4\n";
		
		buffWriter.write(tempLine);
		for(int i=0;i<array_run.size();i++){
			run=array_run.get(i);
			//tempLine=run.runId+"\t"+run.k_wIG+"\t"+run.k_sD+"\t"+run.k_sMV+"\t"+run.k_nQC+"\t"+run.k_iA_SUM+"\t"+run.k_sDMultiWIG+"\t"+run.k_sDMultiWIG_IASUM+"\t"+run.k_cF+"\t"+run.k_cF_IASUM+"\n";
			tempLine=run.runId+"\t"+run.s_sD2+"\t"+run.s_wIG+"\t"+run.s_sMV+"\t"+run.s_nQC+"\t"+run.s_c+"\t"+run.s_c2+"\t"+run.s_c3+"\t"+run.s_c4+"\n";
			buffWriter.write(tempLine);
		}
		buffWriter.close();
		System.out.println("��ȡpearson,kendall,spearmanϵ���ļ�,�Ա�����ʽ��ϵ�������ļ�,�����..");
	}
	/**
	 * 
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException{
		//
		String input=null;
		String packageName=null;
		packageName="robustTrack2004";
		input="./"+packageName+"/�½��ı��ĵ� (3).txt";
		
		/*
		//��input�ļ���,���1/2(hard+easy)
		accuracy_mean_hard_easy(input);
		*/
		
		//����С�����3λ,ʹ������������
		normalizeCoefficient(input);
		//��input�ļ��е���Ϣ�Ա�����ʽ�����ļ���
		input="./"+packageName+"/�½��ı��ĵ� (3).txt_withCoefficientNormalized";
		load_input(input);
		
		/*
		//����С�����3λ,ʹ������������
		normalizeCoefficient(input);
		//��input�ļ��е���Ϣ�Ա�����ʽ�����ļ���
		input="./"+packageName+"/�½��ı��ĵ� (3).txt_withCoefficientNormalized";
		visualize_run_coeff(input);
		*/
	}

}

class Accuracy{
	String overall;
	String hard;
	String medium;
	String easy;
	String mean;//hard+easy��׼ȷ��
	public Accuracy(){
		
	}
	public Accuracy(ListIterator<String> iter){
		overall=iter.next().split("=")[1].trim();
		hard=iter.next().split("=")[1].trim();
		medium=iter.next().split("=")[1].trim();
		easy=iter.next().split("=")[1].trim();
		mean=iter.next().split("=")[1].trim();
	}
}
class Organization{
	String name;
	Accuracy support_svm;
	Accuracy sD2;
	Accuracy wIG;
	Accuracy sMV;
	Accuracy nQC;
	Accuracy c;
	Accuracy c2;
	Accuracy c4;
	
}

