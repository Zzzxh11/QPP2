package newPredictor;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

/**
 * ��Ԥ�ⷽ���Ĺ�ʽΪ:Math.pow(1+score_20,1+(score_1-score_20)/scoreD),
 * @author 1
 *
 */
public class C3 {

	private int k=20;//kΪ�ضϲ���

	public int getK() {
		return k;
	}
	public void setK(int k) {
		this.k = k;
	}
	public double mean(double[] score){
		int length=score.length;
		double sum=0;
		for(int i=0;i<length;i++)
			sum=sum+score[i];
		return sum/length;
	}
	/**
	 * ����score����ͽضϲ���k����C3
	 * @param score
	 * @return
	 */
	public double computeC3(double[] score){
		double base=0;
		double exponent=0;
		double scoreD=mean(score);

		base=1+score[k-1];
		exponent=1+(score[0]-score[k-1])/scoreD;
		return Math.pow(base, exponent);
	}

	/**
	 * ����input.runId����ÿ��query��C3ֵ,����c3Score�����ļ�
	 * 
	 * */
	public void getC3Scores(String input, String output) {
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
		double c3Score = 0;// ��ʱ���һ��query��c3ֵ
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
				// queryId��ͬ,����preQueryId��c3Score,д���ļ�,���arrayList��Ϣ,����terms��Ϣ
				if (!preQueryId.equalsIgnoreCase(terms[0])) {
					// ��arrayListת��Ϊdouble����
					scoreCount = arrayList.size();
					scores = new double[scoreCount];
					for (int i = 0; i < scoreCount; i++)
						scores[i] = arrayList.get(i);
					//���˲�ѯ�µ��ĵ���scoreCountС��k,��k��ΪscoreCount
					if(scoreCount<k) k=scoreCount;
					// ����computC3()�����query��c3ֵ
					c3Score = computeC3(scores);
					// ��queryId��c3Scoreд���ļ�
					buffWriter.write("queryId:\t" + preQueryId + "\tC3:\t" + c3Score + "\n");
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
			// ���queryId��Ӧ��scoresδ����,������c3Score,��д���ļ�
			// ��arrayListת��Ϊdouble����
			scoreCount = arrayList.size();
			scores = new double[scoreCount];
			for (int i = 0; i < scoreCount; i++)
				scores[i] = arrayList.get(i);
			//���˲�ѯ�µ��ĵ���scoreCountС��k,��k��ΪscoreCount
			if(scoreCount<k) k=scoreCount;
			// ����computC3()�����query��c3ֵ
			c3Score = computeC3(scores);
			// ��queryId��c3Scoreд���ļ�
			buffWriter.write("queryId:\t" + preQueryId + "\tC3:\t" + c3Score + "\n");
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
