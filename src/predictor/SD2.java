package predictor;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * ����Ҫ��֤����m��m[0]�ķ���>0
 * @author 1
 *
 */
public class SD2 {
	
	private double x=0.5;//xΪSD�Ľضϲ���
	private HashMap<String,String> queryMap=null;//queryMap����queryId��queryLen
	
	public double getX() {
		return x;
	}
	public void setX(double x) {
		this.x = x;
	}
	public HashMap<String, String> getQueryMap() {
		return queryMap;
	}
	public void setQueryMap(HashMap<String, String> queryMap) {
		this.queryMap = queryMap;
	}
	
	/**
	 * ����Ҫ��֤����m��m[0]�ķ���>0
	 * ����SD2ֵ
	 * @param m
	 * @return
	 */
	public double computeSD2(double[] m,String queryId){
		if(m[0]<=0){System.out.println("m[0]<=0,��������ֹ.."); System.exit(1);}
		//��ȡqlen��Ϣ
		int qlen=0;
		if(queryMap!=null){
			qlen=Integer.parseInt(queryMap.get(queryId));
		}else{
			qlen=1;
		}
		
		double firstScore=m[0];
		double  threshold= firstScore*x;
		int k=m.length;//������ֵx�õ���Ӧ��kֵ,k�ĳ�ʼֵ��Ϊm.length
		for(int i=0;i<m.length;i++){
			if(m[i]<threshold){
				k=i;
				break;
			}
		}
		//��ʼ���㷽��
		//��������mǰk���ƽ��ֵ
		double u=0;
		for(int i=0;i<k;i++){
			u=u+m[i];
		}
		u=u/k;
		//���㷽��
		double dev=0;
		for(int i=0;i<k;i++){
			dev=dev+(m[i]-u)*(m[i]-u);
		}
		dev=Math.sqrt(dev/(k-1))/Math.sqrt(qlen);
		return dev;
	}
	
	/**
	 * ����input.runId����ÿ��query��SD2ֵ, ����SD2Score�����ļ�
	 * */
	public void getSD2Scores(String input, String output) {
		FileReader fileReader = null;
		BufferedReader buffReader = null;
		FileWriter fileWriter = null;
		String tempLine = null;
		String[] terms = null;// ����tempLine
		String preQueryId = null;
		ArrayList<Double> arrayList = new ArrayList<Double>();
		double score = 0;// ��ʱ���terms[4]��score
		double[] scores = null;// ��ʱ���һ��query��Ӧ��score����
		int scoreCount = 0;// ��ʱ���score����ĳ���
		double SD2Score = 0;// ��ʱ���һ��query��SD2ֵ

		try {
			fileReader = new FileReader(input);
			buffReader = new BufferedReader(fileReader);
			fileWriter = new FileWriter(output, false);
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
				// queryId��ͬ,����preQueryId��SDScore,д���ļ�,���arrayList��Ϣ,����terms��Ϣ
				if (!preQueryId.equalsIgnoreCase(terms[0])) {
					// ��arrayListת��Ϊdouble����
					scoreCount = arrayList.size();
					scores = new double[scoreCount];
					for (int i = 0; i < scoreCount; i++)
						scores[i] = arrayList.get(i);
					
					// ����computeSD2()�����query��SD2ֵ
					SD2Score = computeSD2(scores,preQueryId);
					// ��queryId��SD2Scoreд���ļ�
					fileWriter.write("queryId:\t" + preQueryId + "\tSD2:\t"
							+ SD2Score + "\n");
					// ���arrayList
					arrayList.clear();
					//��ʼ����terms��Ϣ
					preQueryId = terms[0];
					score = Double.parseDouble(terms[4]);
					arrayList.add(score);
				}
			}
			// ���queryId��Ӧ��scoresδ����,������SD2Score,��д���ļ�
			// ��arrayListת��Ϊdouble����
			scoreCount = arrayList.size();
			scores = new double[scoreCount];
			for (int i = 0; i < scoreCount; i++)
				scores[i] = arrayList.get(i);
			// ����computeSD2()�����query��SD2ֵ
			SD2Score = computeSD2(scores,preQueryId);
			// ��queryId��SD2Scoreд���ļ�
			fileWriter.write("queryId:\t" + preQueryId + "\tSD2:\t" + SD2Score
					+ "\n");
		} catch (IOException e) {
			System.err.println("�������ݳ���!");
			e.printStackTrace();
		} finally {
			try {
				fileWriter.close();
				buffReader.close();
			} catch (IOException e) {
				System.err.println("�ر�IO���Ӵ���!");
				e.printStackTrace();
			}
		}
	}

	public static void main(String[] args) {
		/*SD2 sD2 = new SD2();
		sD2.x=0.5;
		
		System.out.println("����input.runId����ÿ��query��SD2ֵ,����SD2Score�����ļ�,�����..");
		*/
		
	}
}
