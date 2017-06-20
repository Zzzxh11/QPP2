package newPredictor;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

/**
 * ��Ԥ�ⷽ���Ĺ�ʽΪ:Math.pow(Math.E,sum(score(d)*Math.log(score(d)/u)))
 * @author 1
 *
 */
public class C4 {
	
	private int k=20;//kΪ�ضϲ���
	
	public int getK() {
		return k;
	}
	public void setK(int k) {
		this.k = k;
	}
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
	 * ����score����ͽضϲ���k����C4
	 * @param score
	 * @return
	 */
	public double computeC4(double[] score){
		double sum=0;
		double u=uMean(score);
		
		for(int i=0;i<k;i++){
			sum=sum+score[i]*Math.log(score[i]/u);
		}
		return Math.pow(Math.E, sum);
	}
	
	/**
	 * ����input.runId����ÿ��query��C4ֵ,����c4Score�����ļ�
	 * 
	 * */
	public void getC4Scores(String input, String output) {
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
		double c4Score = 0;// ��ʱ���һ��query��c4ֵ
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
				// queryId��ͬ,����preQueryId��c4Score,д���ļ�,���arrayList��Ϣ,����terms��Ϣ
				if (!preQueryId.equalsIgnoreCase(terms[0])) {
					// ��arrayListת��Ϊdouble����
					scoreCount = arrayList.size();
					scores = new double[scoreCount];
					for (int i = 0; i < scoreCount; i++)
						scores[i] = arrayList.get(i);
					//���˲�ѯ�µ��ĵ���scoreCountС��k,��k��ΪscoreCount
					if(scoreCount<k) k=scoreCount;
					// ����computC4()�����query��c4ֵ
					c4Score = computeC4(scores);
					// ��queryId��c4Scoreд���ļ�
					buffWriter.write("queryId:\t" + preQueryId + "\tC4:\t" + c4Score + "\n");
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
			// ���queryId��Ӧ��scoresδ����,������c4Score,��д���ļ�
			// ��arrayListת��Ϊdouble����
			scoreCount = arrayList.size();
			scores = new double[scoreCount];
			for (int i = 0; i < scoreCount; i++)
				scores[i] = arrayList.get(i);
			//���˲�ѯ�µ��ĵ���scoreCountС��k,��k��ΪscoreCount
			if(scoreCount<k) k=scoreCount;
			// ����computC4()�����query��c4ֵ
			c4Score = computeC4(scores);
			// ��queryId��c4Scoreд���ļ�
			buffWriter.write("queryId:\t" + preQueryId + "\tC4:\t" + c4Score + "\n");
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
