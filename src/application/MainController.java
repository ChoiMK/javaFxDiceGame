package application;

import java.net.URL;
import java.util.Arrays;
import java.util.ResourceBundle;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.util.Duration;

public class MainController implements Initializable{
	
	//Main.fxml에 만들어 놓은 주사위 ImageView들
	@FXML private ImageView imageView_dice_1_1;
	@FXML private ImageView imageView_dice_1_2;
	@FXML private ImageView imageView_dice_1_3;
	@FXML private ImageView imageView_dice_1_4;
	@FXML private ImageView imageView_dice_1_5;
	@FXML private ImageView imageView_dice_2_1;
	@FXML private ImageView imageView_dice_2_2;
	@FXML private ImageView imageView_dice_2_3;
	@FXML private ImageView imageView_dice_2_4;
	@FXML private ImageView imageView_dice_2_5;
	
	//주사위 패를 비교하여 승부를 정하기 위한 라벨들
	@FXML private Label label_whosWin;
	@FXML private Label label_diceRule1;
	@FXML private Label label_diceRule2;
	
	//판돈을 표시할 라벤
	@FXML private Label label_comMoney;
	@FXML private Label label_userMoney;
	@FXML private Label label_betMoney;
	
	//ImageView를 for문으로 처리하기 쉽게 하기 위한 배열
	private ImageView[] arrImageView_1;
	private ImageView[] arrImageView_2;
	
	//주사위 이미지를 1~6까지 저장할 배열
	private Image[] arrImage_dice;
	
	//주사위 패를 비교하기 위해 임시로 패를 저장할 배열
	private int[] arrDice_1 = new int[5];
	private int[] arrDice_2 = new int[5];
	
	
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		System.out.println(">> MainController>initializeble"); 
		
		//주사위 이미지를 미리 배열에 담아둔다.
		arrImage_dice = new Image[6];
		for(int i=0; i<arrImage_dice.length; i++){
			arrImage_dice[i] = new Image("resource/dice_"+(i+1)+".jpg");
		}
		
		//화면에 표시된 주사위 ImageView를 관리하기 쉽도록 배열에 넣어둔다.
		arrImageView_1 = new ImageView[5];
		arrImageView_1[0] = imageView_dice_1_1;
		arrImageView_1[1] = imageView_dice_1_2;
		arrImageView_1[2] = imageView_dice_1_3;
		arrImageView_1[3] = imageView_dice_1_4;
		arrImageView_1[4] = imageView_dice_1_5;
		
		arrImageView_2 = new ImageView[5];
		arrImageView_2[0] = imageView_dice_2_1;
		arrImageView_2[1] = imageView_dice_2_2;
		arrImageView_2[2] = imageView_dice_2_3;
		arrImageView_2[3] = imageView_dice_2_4;
		arrImageView_2[4] = imageView_dice_2_5;
	
		label_comMoney.setText("100000");
		label_userMoney.setText("50000");
		label_betMoney.setText("1000");
	}
	
	@FXML
	public void onclickThrowDice(ActionEvent event){
		
		label_diceRule1.setText("");
		label_diceRule2.setText("");
		label_whosWin.setText("");
		
		//주사위를 던지면 일단 램덤하게 주사위를 표시한후 보기 좋도록 정렬하여 재배치한다.
		//이때 Timeline으로 애니메이션 효과를 준다.
		Timeline timeline = new Timeline();
		
		for(int i=0; i<arrImageView_1.length; i++){
			KeyValue keyValue1 = new KeyValue(arrImageView_1[i].imageProperty(),null);
			KeyValue keyValue2 = new KeyValue(arrImageView_2[i].imageProperty(),null);
			KeyFrame keyFrame1 = new KeyFrame(Duration.seconds(0), keyValue1);
			KeyFrame keyFrame2 = new KeyFrame(Duration.seconds(0), keyValue2);
			timeline.getKeyFrames().addAll(keyFrame1,keyFrame2);
			
		}
		
		for(int i=0; i<arrImageView_1.length; i++){
			int imageNumber = (int)(Math.random()*6);
			//주사위를 던진 순간은 램덤하게 상태 그대로 보여줄 것이기 때문에
			//Duration을 0초로 하여 KeyFrame을 timeline에 추가한다.
			KeyValue keyValue = new KeyValue(arrImageView_1[i].imageProperty()
											 ,arrImage_dice[imageNumber]);
			KeyFrame keyFrame = new KeyFrame(Duration.seconds((i+1)*0.3), keyValue);
			timeline.getKeyFrames().add(keyFrame);
			
			arrDice_1[i] = imageNumber;
		}
		
		for(int i=0; i<arrImageView_2.length; i++){
			int imageNumber = (int)(Math.random()*6);
			//주사위를 던진 순간은 램덤하게 상태 그대로 보여줄 것이기 때문에
			//Duration을 0초로 하여 KeyFrame을 timeline에 추가한다.
			KeyValue keyValue = new KeyValue(arrImageView_2[i].imageProperty()
											 ,arrImage_dice[imageNumber]);
			KeyFrame keyFrame = new KeyFrame(Duration.seconds((i+1)*0.3), keyValue);
			timeline.getKeyFrames().add(keyFrame);
			arrDice_2[i] = imageNumber;
		}
		
		//주사위 배열을 보기좋게 하기위해 정렬한다.
		Arrays.sort(arrDice_1);
		Arrays.sort(arrDice_2);
		
		//정렬한 주사위모양으로 이미지를 다시 바꿔준다.
		for(int i=0; i<arrDice_1.length; i++){
			KeyValue keyValue1 = new KeyValue(arrImageView_1[i].imageProperty(),arrImage_dice[arrDice_1[i]]);
			KeyFrame keyFrame1 = new KeyFrame(Duration.seconds(3), keyValue1);
			timeline.getKeyFrames().add(keyFrame1);
			
			KeyValue keyValue2 = new KeyValue(arrImageView_2[i].imageProperty(),arrImage_dice[arrDice_2[i]]);
			KeyFrame keyFrame2 = new KeyFrame(Duration.seconds(3), keyValue2);
			timeline.getKeyFrames().add(keyFrame2);
			
			//arrImageView_1[i].setImage(arrImage_dice[arrDice_1[i]]);
			//arrImageView_2[i].setImage(arrImage_dice[arrDice_2[i]]);
		}
		
		//서로의 패를 비교하여 누가 이겼는지 판별
		CompareDice compareDice = new CompareDice();
		DiceRuleVO vo1 = compareDice.getRule(arrDice_1);
		DiceRuleVO vo2 = compareDice.getRule(arrDice_2);
		int result = compareDice.compareRule(vo1, vo2);
		String whosWinText = "";
		if(result>0){
			whosWinText ="컴퓨터가 승리하였습니다";
			int comMoney = Integer.parseInt(label_comMoney.getText());
			int userMoney = Integer.parseInt(label_userMoney.getText());
			int betMoney = Integer.parseInt(label_betMoney.getText());
			userMoney -= betMoney;
			comMoney += betMoney;
			
			KeyValue keyvalue1 = new KeyValue(label_comMoney.textProperty(), comMoney+"");
			KeyFrame keyFrame1 = new KeyFrame(Duration.seconds(3),keyvalue1); 
			KeyValue keyvalue2 = new KeyValue(label_userMoney.textProperty(), userMoney+"");
			KeyFrame keyFrame2 = new KeyFrame(Duration.seconds(3),keyvalue2);
			timeline.getKeyFrames().add(keyFrame1);
			timeline.getKeyFrames().add(keyFrame2);
			
			//label_comMoney.setText(comMoney+"");
			//label_userMoney.setText(userMoney+"");
			
//			if(userMoney<=0){
//				Alert alert = new Alert(AlertType.WARNING);
//				alert.setTitle("");
//				alert.setHeaderText("컴퓨터승리");
//				alert.showAndWait();
//				Stage stage = (Stage)label_userMoney.getScene().getWindow();
//				stage.close();
//			}
			
		}else if(result<0){
			whosWinText = "사용자가 승리하였습니다";
			int comMoney = Integer.parseInt(label_comMoney.getText());
			int userMoney = Integer.parseInt(label_userMoney.getText());
			int betMoney = Integer.parseInt(label_betMoney.getText());
			userMoney += betMoney;
			comMoney -= betMoney;
			
			KeyValue keyvalue1 = new KeyValue(label_comMoney.textProperty(), comMoney+"");
			KeyFrame keyFrame1 = new KeyFrame(Duration.seconds(3),keyvalue1); 
			KeyValue keyvalue2 = new KeyValue(label_userMoney.textProperty(), userMoney+"");
			KeyFrame keyFrame2 = new KeyFrame(Duration.seconds(3),keyvalue2);
			timeline.getKeyFrames().add(keyFrame1);
			timeline.getKeyFrames().add(keyFrame2);
			
			//label_comMoney.setText(comMoney+"");
			//label_userMoney.setText(userMoney+"");
		
		}else{
			whosWinText = "무승부입니다";
		}
		
		KeyValue keyValue1 = new KeyValue(label_whosWin.textProperty(), whosWinText);
		KeyValue keyValue2 = new KeyValue(label_diceRule1.textProperty(), vo1.getDiceRule().name());
		KeyValue keyValue3 = new KeyValue(label_diceRule2.textProperty(), vo2.getDiceRule().name());
		KeyFrame keyFrame1 = new KeyFrame(Duration.seconds(3), keyValue1);
		KeyFrame keyFrame2 = new KeyFrame(Duration.seconds(3), keyValue2);
		KeyFrame keyFrame3 = new KeyFrame(Duration.seconds(3), keyValue3);
		timeline.getKeyFrames().addAll(keyFrame1,keyFrame2,keyFrame3);
		
		
		//label_whosWin.setText(whosWinText);
		//label_diceRule1.setText(vo1.getDiceRule().name());
		//label_diceRule2.setText(vo2.getDiceRule().name());
		
		timeline.play();
	}
	
	//베팅 올림버튼 클릭 이벤트
	@FXML
	public void clickUpBet(ActionEvent event){
		int betMoney = Integer.parseInt(label_betMoney.getText());
		if(betMoney<50000){
			betMoney += 1000;
			label_betMoney.setText(betMoney+"");
		}
	}

	//베팅 내림버튼 클릭 이벤트
	@FXML
	public void clickDownBet(ActionEvent event){
		int betMoney = Integer.parseInt(label_betMoney.getText());
		if(betMoney>1000){
			betMoney -= 1000;
			label_betMoney.setText(betMoney+"");
		}
	}


}
