package newPredictor;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * ע�⣺��ʦȥ���˷����ϵĸ��� by Zoey
 * ��Ԥ�ⷽ���Ĺ�ʽΪ:Math.sqrt(1/k*sum((score(d)^2)*(score(d)-u)^2))/score(D),
 * ������SD2��WIG���ϡ�
 * @author 1
 *
 */
public class C2 {

	private int k=20;//kΪ�ضϲ���
	private HashMap<String,String> queryMap=null;//queryMap����queryId��queryLen

	public int getK() {
		return k;
	}
	public void setK(int k) {
		this.k = k;
	}
	public HashMap<String, String> getQueryMap() {
		return queryMap;
	}
	public void setQueryMap(HashMap<String, String> queryMap) {
		this.queryMap = queryMap;
	}
	
	public double mean(double[] score){
		int length=score.length;
		double sum=0;
		for(int i=0;i<length;i++)
			sum=sum+score[i];
		return sum/length;
	}
	
	//ǰk���ĵ��ľ�ֵ
	public double uMean(double[] score){
		double uMean=0;
		double sum=0;
		for(int i=0;i<k;i++){
			sum=sum+score[i];
		}
		uMean=sum/k;
		return uMean;
	}
	/**
	 * ����score����ͽضϲ���k����C2
	 * */
	public double computeC2(double[] score,String queryId){
		double u=uMean(score);
		double scoreD=mean(score);
		double sum=0;
		double numerator=0;
		double denom=0;
		//��ȡqlen��Ϣ
		int qlen=0;
		if(queryMap!=null){
			qlen=Integer.parseInt(queryMap.get(queryId));
		}else{
			qlen=1;
		}

		for(int i=0;i<k;i++){
			//sum=sum+Math.pow(score[i], 2)*Math.pow(score[i]-u, 2);
			sum=sum+score[i]*Math.pow(score[i]-u, 2);
			
		}
		//by Zoey
		//numerator=Math.sqrt(sum/k);
		numerator=sum/k;
		denom=scoreD*Math.sqrt(qlen);
		return numerator/denom;
	}

	/**
	 * ����input.runId����ÿ��query��C2ֵ,����c2Score�����ļ�
	 * 
	 * */
	public void getC2Scores(String input, String output) {
		FileReader fileReader = null;
		BufferedReader buffReader = null;
		FileWriter fileWriter = null;
		BufferedWriter buffWriter=null;
		String tempLine = null;
		String[] terms = null;// ����tempLine
		String preQueryId = null;
		ArrayList<Double> arrayList = new ArrayList<Double>();
		double score = 0;// ��ʱ���terms[4]��score
		double[] scores = null;// ��ʱ���һ��query��Ӧ��score����
		int scoreCount = 0;// ��ʱ���score����ĳ���
		double c2Score = 0;// ��ʱ���һ��query��c2ֵ
		int k_original=k;//�洢�����kֵ

		try {
			fileReader = new FileReader(input);
			buffReader = new BufferedReader(fileReader);
			fileWriter = new FileWriter(output, false);
			buffWriter=new BufferedWriter(fileWriter);
			while ((tempLine = buffReader.readLine()) != null) {
				terms = tempLine.split("\t| ");
				// ���preQueryIdΪnull
				if (preQueryId == null)
					preQueryId = terms[0];
				// queryId��ͬ,����score
				if (preQueryId.equalsIgnoreCase(terms[0])) {
					score = Double.parseDouble(terms[4]);
					arrayList.add(score);
				}
				// queryId��ͬ,����preQueryId��c2Score,д���ļ�,���arrayList��Ϣ,����terms��Ϣ
				if (!preQueryId.equalsIgnoreCase(terms[0])) {
					// ��arrayListת��Ϊdouble����
					scoreCount = arrayList.size();
					scores = new double[scoreCount];
					for (int i = 0; i < scoreCount; i++)
						scores[i] = arrayList.get(i);
					//���˲�ѯ�µ��ĵ���scoreCountС��k,��k��ΪscoreCount
					if(scoreCount<k) k=scoreCount;
					// ����computC2()�����query��c2ֵ
					c2Score = computeC2(scores,preQueryId);
					// ��queryId��c2Scoreд���ļ�
					buffWriter.write("queryId:\t" + preQueryId + "\tC2:\t" + c2Score + "\n");
					//��k��Ϊ��ʼkֵ,��k��Ϊ��ʼֵk_original
					if(k!=k_original) k=k_original;
					// ���arrayList
					arrayList.clear();
					// ��ʼ����terms��Ϣ
					preQueryId = terms[0];
					score = Double.parseDouble(terms[4]);
					arrayList.add(score);
				}
			}
			// ���queryId��Ӧ��scoresδ����,������c2Score,��д���ļ�
			// ��arrayListת��Ϊdouble����
			scoreCount = arrayList.size();
			scores = new double[scoreCount];
			for (int i = 0; i < scoreCount; i++)
				scores[i] = arrayList.get(i);
			//���˲�ѯ�µ��ĵ���scoreCountС��k,��k��ΪscoreCount
			if(scoreCount<k) k=scoreCount;
			// ����computC2()�����query��c2ֵ
			c2Score = computeC2(scores,preQueryId);
			// ��queryId��c2Scoreд���ļ�
			buffWriter.write("queryId:\t" + preQueryId + "\tC2:\t" + c2Score + "\n");
		} catch (IOException e) {
			System.err.println("�������ݳ���!");
			e.printStackTrace();
		} finally {
			try {
				buffWriter.close();
				buffReader.close();
			} catch (IOException e) {
				System.err.println("�ر�IO���Ӵ���!");
				e.printStackTrace();
			}
		}
	}
	
	public static void main(String[] args){
		
	}

}
