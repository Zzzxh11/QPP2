package process;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Predictor_compare {

	public static void processSummary(String runId,String packageName,int round){
		//����summary�ļ�
		if(runId.equals("uogTBQEL")){
			predictor.SummaryAnalysis.round = round;
			predictor.SummaryAnalysis.extractAveragePrecisionForTB("./"+packageName+"/summary."+runId, "./"+packageName+"/map."+runId);
			predictor.SummaryAnalysis.setTermSize(5);
			predictor.SummaryAnalysis.normalizeAveragePrecision("./"+packageName+"/map."+runId, "./"+packageName+"/map.normalized."+runId);
		}
		else{
		predictor.SummaryAnalysis.round = round;
		predictor.SummaryAnalysis.extractAveragePrecision("./"+packageName+"/summary."+runId, "./"+packageName+"/map."+runId);
		predictor.SummaryAnalysis.setTermSize(5);
		predictor.SummaryAnalysis.normalizeAveragePrecision("./"+packageName+"/map."+runId, "./"+packageName+"/map.normalized."+runId);
		}
	}

	public static void processPrediction(String runId,String packageName) throws IOException{
		if(runId.equals("uogTBQEL")){
			// ����SD
			predictor.SD predictorSD = new predictor.SD();
			predictorSD.setK(1000);
			predictorSD.getSDScores("./" + packageName + "/input." + runId, "./"+ packageName + "/sDScore." + runId);
			System.out.println("����input����ÿ��query��SDֵ,����SDScore�����ļ�,�����..");
			//����SMV
			predictor.SMV predictorSMV = new predictor.SMV();
			predictorSMV.setK(1000);//��predictorSMV��k��Ϊ100
			predictorSMV.getSMVScores("./"+packageName+"/input."+runId,"./"+packageName+"/sMVScore."+runId);
			System.out.println("����input����ÿ��query��SMVֵ,����SMVScore�����ļ�,�����..");
			
			//����NQC
			predictor.NQC predictorNQC=new predictor.NQC();
			predictorNQC.setK(1000);//��predictorNQC��k��Ϊ100
			predictorNQC.getNQCScores("./"+packageName+"/input."+runId, "./"+packageName+"/nQCScore."+runId);
			System.out.println("����input����ÿ��query��NQCֵ,����NQCScore�����ļ�,�����..");
			
			//����C2
			newPredictor.C2 newPredictorC2=new newPredictor.C2();
			newPredictorC2.setK(1000);//�ضϲ���
			newPredictorC2.setQueryMap(null);
			newPredictorC2.getC2Scores("./"+packageName+"/input."+runId,"./"+packageName+"/c2Score."+runId);
		}
		else{
		// ����SD
		predictor.SD predictorSD = new predictor.SD();
		predictorSD.setK(100);
		predictorSD.getSDScores("./" + packageName + "/input." + runId, "./"+ packageName + "/sDScore." + runId);
		System.out.println("����input����ÿ��query��SDֵ,����SDScore�����ļ�,�����..");
		//����SMV
		predictor.SMV predictorSMV = new predictor.SMV();
		predictorSMV.setK(100);//��predictorSMV��k��Ϊ100
		predictorSMV.getSMVScores("./"+packageName+"/input."+runId,"./"+packageName+"/sMVScore."+runId);
		System.out.println("����input����ÿ��query��SMVֵ,����SMVScore�����ļ�,�����..");
		
		//����NQC
		predictor.NQC predictorNQC=new predictor.NQC();
		predictorNQC.setK(100);//��predictorNQC��k��Ϊ100
		predictorNQC.getNQCScores("./"+packageName+"/input."+runId, "./"+packageName+"/nQCScore."+runId);
		System.out.println("����input����ÿ��query��NQCֵ,����NQCScore�����ļ�,�����..");
		
		//����C2
		newPredictor.C2 newPredictorC2=new newPredictor.C2();
		newPredictorC2.setK(100);//�ضϲ���
		newPredictorC2.setQueryMap(null);
		newPredictorC2.getC2Scores("./"+packageName+"/input."+runId,"./"+packageName+"/c2Score."+runId);
		}
		
		// ����SD2
		predictor.SD2 predictorSD2 = new predictor.SD2();
		predictorSD2.setX(0.5);// ��predictorSD2��x��Ϊ0.5
		predictorSD2.setQueryMap(null);
		predictorSD2.getSD2Scores("./" + packageName + "/input." + runId, "./"+ packageName + "/sD2Score." + runId);
		System.out.println("����input����ÿ��query��SD2ֵ,����SD2Score�����ļ�,�����..");
		
		//����WIG
		predictor.WIG predictorWIG=new predictor.WIG();
		predictorWIG.setK(5);//��predictorWIG��k��Ϊ5
		//�����QueryLength.getQueryLength()ΪpackageName���е�,
		predictorWIG.setQueryMap(null);
		predictorWIG.getWIGScores("./"+packageName+"/input."+runId,"./"+packageName+"/wIGScore."+runId);
		System.out.println("����input����ÿ��query��WIGֵ,����WIGScore�����ļ�,�����..");
		

		
		//����C
		newPredictor.C newPredictorC=new newPredictor.C();
		newPredictorC.setAlpha(0.5);//SD_2��WIG��ƽ�����
		newPredictorC.getCScores("./"+packageName+"/input."+runId,"./"+packageName+"/cScore."+runId);
		
		
		
		//����C3
		newPredictor.C3 newPredictorC3=new newPredictor.C3();
		newPredictorC3.setK(100);//�ضϲ���
		newPredictorC3.getC3Scores("./"+packageName+"/input."+runId,"./"+packageName+"/c3Score."+runId);
		
		//����C4
		newPredictor.C4 newPredictorC4=new newPredictor.C4();
		newPredictorC4.setK(100);//�ضϲ���
		newPredictorC4.getC4Scores("./"+packageName+"/input."+runId,"./"+packageName+"/c4Score."+runId);
		
	}
	/**
	 * �ɹ�getGeneratedResult_batch()��������
	 * @param args
	 * @throws IOException
	 */
	public static void getGeneratedResult(String runId,String packageName) throws IOException {
		//robustTrack2004�Ŀ���֤topic����Ϊ249
		int round=0;
		switch(runId){
		case "pircRB04t3":round=249;break;
		case "uogTBQEL":round=49;break;
		case "CnQst2":round=49;break;
		case "thutd5":round=49;break;
		default:round=50;break;
		
		}
		//round=249;
		//by Zoey
		//����summary�ļ�,��ȡaverage Precision��Ϣ
		processSummary(runId,packageName,round);
		
		//����input�ļ�,����Ԥ���㷨,�õ�Ԥ����Ϣ
		//by Zoey
		processPrediction(runId,packageName);
		
		
		//Ԥ��ֵ��AP��pearson kendall spearmanϵ��:
		System.out.println("Ԥ��ֵ��AP��pearson kendall spearmanϵ��:\n");
	
		//����pearson�㷨
		try {
			//SD
			correlationCoefficient.PearsonAnalysis.loadScoreAndComputePearson("./"+packageName+"/sDScore."+runId,"./"+packageName+"/map.normalized."+runId);
			//sD2��Ӧ��pearson
			correlationCoefficient.PearsonAnalysis.loadScoreAndComputePearson("./"+packageName+"/sD2Score."+runId,"./"+packageName+"/map.normalized."+runId);
			//wIG��Ӧ��pearson
			correlationCoefficient.PearsonAnalysis.loadScoreAndComputePearson("./"+packageName+"/wIGScore."+runId,"./"+packageName+"/map.normalized."+runId);
			//sMV��Ӧ��pearson
			correlationCoefficient.PearsonAnalysis.loadScoreAndComputePearson("./"+packageName+"/sMVScore."+runId,"./"+packageName+"/map.normalized."+runId);
			//nQC��Ӧ��pearson
			correlationCoefficient.PearsonAnalysis.loadScoreAndComputePearson("./"+packageName+"/nQCScore."+runId,"./"+packageName+"/map.normalized."+runId);
			
			//c��Ӧ��pearson
			correlationCoefficient.PearsonAnalysis.loadScoreAndComputePearson("./"+packageName+"/cScore."+runId,"./"+packageName+"/map.normalized."+runId);
			//c2��Ӧ��pearson
			correlationCoefficient.PearsonAnalysis.loadScoreAndComputePearson("./"+packageName+"/c2Score."+runId,"./"+packageName+"/map.normalized."+runId);
			//c3��Ӧ��pearson
			//by Zoey
			//correlationCoefficient.PearsonAnalysis.loadScoreAndComputePearson("./"+packageName+"/c3Score."+runId,"./"+packageName+"/map.normalized."+runId);
			//c4��Ӧ��pearson
			//correlationCoefficient.PearsonAnalysis.loadScoreAndComputePearson("./"+packageName+"/c4Score."+runId,"./"+packageName+"/map.normalized."+runId);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		//����kendall�㷨
		try {
			//SD
			correlationCoefficient.Kendall.loadScoreAndComputeKendall("./"+packageName+"/sDScore."+runId,"./"+packageName+"/map.normalized."+runId);
			//sD2��Ӧ��kendall
			correlationCoefficient.Kendall.loadScoreAndComputeKendall("./"+packageName+"/sD2Score."+runId,"./"+packageName+"/map.normalized."+runId);
			//wIG��Ӧ��kendall
			correlationCoefficient.Kendall.loadScoreAndComputeKendall("./"+packageName+"/wIGScore."+runId,"./"+packageName+"/map.normalized."+runId);
			//sMV��Ӧ��kendall
			correlationCoefficient.Kendall.loadScoreAndComputeKendall("./"+packageName+"/sMVScore."+runId,"./"+packageName+"/map.normalized."+runId);
			//nQC��Ӧ��kendall
			correlationCoefficient.Kendall.loadScoreAndComputeKendall("./"+packageName+"/nQCScore."+runId,"./"+packageName+"/map.normalized."+runId);
			
			//c��Ӧ��kendall
			correlationCoefficient.Kendall.loadScoreAndComputeKendall("./"+packageName+"/cScore."+runId,"./"+packageName+"/map.normalized."+runId);
			//c2��Ӧ��kendall
			correlationCoefficient.Kendall.loadScoreAndComputeKendall("./"+packageName+"/c2Score."+runId,"./"+packageName+"/map.normalized."+runId);
			//c3��Ӧ��kendall
			//correlationCoefficient.Kendall.loadScoreAndComputeKendall("./"+packageName+"/c3Score."+runId,"./"+packageName+"/map.normalized."+runId);
			//c4��Ӧ��kendall
			//correlationCoefficient.Kendall.loadScoreAndComputeKendall("./"+packageName+"/c4Score."+runId,"./"+packageName+"/map.normalized."+runId);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		//����spearman�㷨
		try {
			//SD
			new correlationCoefficient.Spearman().loadScoreAndComputeSpearman("./"+packageName+"/sDScore."+runId,"./"+packageName+"/map.normalized."+runId);
			//sD2��Ӧ��spearman
			new correlationCoefficient.Spearman().loadScoreAndComputeSpearman("./"+packageName+"/sD2Score."+runId,"./"+packageName+"/map.normalized."+runId);
			//wIG��Ӧ��spearman
			new correlationCoefficient.Spearman().loadScoreAndComputeSpearman("./"+packageName+"/wIGScore."+runId,"./"+packageName+"/map.normalized."+runId);
			//sMV��Ӧ��spearman
			new correlationCoefficient.Spearman().loadScoreAndComputeSpearman("./"+packageName+"/sMVScore."+runId,"./"+packageName+"/map.normalized."+runId);
			//nQC��Ӧ��spearman
			new correlationCoefficient.Spearman().loadScoreAndComputeSpearman("./"+packageName+"/nQCScore."+runId,"./"+packageName+"/map.normalized."+runId);
			
			//c��Ӧ��spearman
			new correlationCoefficient.Spearman().loadScoreAndComputeSpearman("./"+packageName+"/cScore."+runId,"./"+packageName+"/map.normalized."+runId);
			//c2��Ӧ��spearman
			new correlationCoefficient.Spearman().loadScoreAndComputeSpearman("./"+packageName+"/c2Score."+runId,"./"+packageName+"/map.normalized."+runId);
			//c3��Ӧ��spearman
			//new correlationCoefficient.Spearman().loadScoreAndComputeSpearman("./"+packageName+"/c3Score."+runId,"./"+packageName+"/map.normalized."+runId);
			//c4��Ӧ��spearman
			//new correlationCoefficient.Spearman().loadScoreAndComputeSpearman("./"+packageName+"/c4Score."+runId,"./"+packageName+"/map.normalized."+runId);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	/**
	 * ��������Ԥ����
	 * @throws InterruptedException 
	 */
	public static void getGeneratedResult_batch()throws IOException, InterruptedException{
		FileReader fileReader=null;
		BufferedReader buffReader=null;
		String runIdFile=null;
		String tempLine=null;
		String packageName=null;
		String runId=null;
		int n=0;
		
		runIdFile="runId.txt";
		//by Zoey
		//packageName="robustTrack2004/13org_best_map_runs";
		packageName="six_runs";
		fileReader=new FileReader("./"+packageName+"/"+runIdFile);
		buffReader=new BufferedReader(fileReader);
		
		while((tempLine=buffReader.readLine())!=null){
			runId=tempLine.split("\\.")[1];
			System.out.println("\n\n"+(++n)+"��trackΪ"+runId);
			//by Zoey
			packageName=packageName+"/"+runId;
			getGeneratedResult(runId,packageName);
			packageName="six_runs";
			//Thread.sleep(15000);
		}
		buffReader.close();
		System.out.println("�����������,�����!");
	}
	/**
	 * 
	 * @param args
	 * @throws InterruptedException 
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException, InterruptedException{
		//��������Ԥ����
		getGeneratedResult_batch();
		
	}

}
