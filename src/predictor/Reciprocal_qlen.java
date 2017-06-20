package predictor;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;

/**
 * ����ÿ��queryId��Ӧ��1.0/qlenֵ��
 * @author 1
 *
 */
public class Reciprocal_qlen {
	
	private HashMap<String,String> queryMap=null;//queryMap����queryId��queryLen

	public HashMap<String, String> getQueryMap() {
		return queryMap;
	}
	public void setQueryMap(HashMap<String, String> queryMap) {
		this.queryMap = queryMap;
	}
	
	public double computeReciprocal_qlen(String queryId){
		int qlen=0;
		if(queryMap!=null){
			qlen=Integer.parseInt(queryMap.get(queryId));
		}else{
			qlen=1;
		}
		double reciprocal=0;
		reciprocal=1.0/qlen;
		return reciprocal;
	}
	
	/**
	 * ����input.runId����ÿ��query��Reciprocal_qlenֵ, ����Recip_score�����ļ�
	 * */
	public void getReciprocal_qlenScores(String input, String output) {
		FileReader fileReader = null;
		BufferedReader buffReader = null;
		FileWriter fileWriter = null;
		String tempLine = null;
		String[] terms = null;// ����tempLine
		String preQueryId = null;
		double Recip_score = 0;// ��ʱ���һ��query��Reciprocal_qlenֵ

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
					
				}
				// queryId��ͬ,����preQueryId��Recip_score,д���ļ�,����terms��Ϣ
				if (!preQueryId.equalsIgnoreCase(terms[0])) {
					// ����computeReciprocal_qlen()�����query��Reciprocal_qlenֵ
					Recip_score = computeReciprocal_qlen(preQueryId);
					// ��queryId��Recip_scoreд���ļ�
					fileWriter.write("queryId:\t" + preQueryId + "\tReciprocal_qlen:\t"+ Recip_score + "\n");
					//��ʼ����terms��Ϣ
					preQueryId = terms[0];
				}
			}
			// ���queryId��Ӧ����Ϣδ����,������Recip_score,��д���ļ�
			
			// ����computeReciprocal_qlen()�����query��Reciprocal_qlenֵ
			Recip_score = computeReciprocal_qlen(preQueryId);
			// ��queryId��Recip_scoreд���ļ�
			fileWriter.write("queryId:\t" + preQueryId + "\tReciprocal_qlen:\t"+ Recip_score + "\n");
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
	
	public static void main(String[] args){
		
	}

}
