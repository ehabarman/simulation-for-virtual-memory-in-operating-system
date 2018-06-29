package application.Controller;

import java.io.IOException;
import java.util.ArrayList;

import application.Driver;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class DetailedController {
	
	
	@FXML
	public void done() throws IOException{
		
		Driver.stage2.close();
		Parent p19 = (Parent) FXMLLoader.load(getClass().getResource("/application/View/Report.fxml"));
		Driver.stage3= new Stage();
		Driver.stage3.sizeToScene();
		Driver.stage3.setScene(new Scene(p19));
		Driver.stage3.setTitle("Report");
		Driver.stage3.show();
	}
	
	@FXML
	public void begin(){
		
		begin.setVisible(false);
		first.setVisible(true);
		back.setVisible(true);
		clock.setVisible(true);
		done.setVisible(true);
		next.setVisible(true);
		last.setVisible(true);
		state.setVisible(true);
		step.setVisible(true);
		missTextField.setVisible(true);
		hitTextField.setVisible(true);
		missLabel.setVisible(true);
		hitLabel.setVisible(true);
		clockLabel.setVisible(true);
		step.setText("0");
		setFields();
		clock.setText("0");
		updateTables(0);
		
	}
	
	@FXML
	public void next(){
		
		if(step.getText().contains("[a-zA-Z]+") == false && Integer.parseInt(step.getText())<0)
			{
			step.setText("0");
			return;
			}
		
		int length = Integer.parseInt(step.getText());
		int clk = Integer.parseInt(clock.getText());
		if(length+clk>=Driver.instants.size())
			clk=Driver.instants.size()-1;
		else
			clk+=length;
		
		clock.setText(""+clk);
		updateTables(clk);
	}
	
	@FXML
	public void last(){
		int clk=Driver.instants.size()-1;
		clock.setText(""+clk);
		updateTables(clk);
	}
	
	@FXML
	public void back(){
		if(step.getText().contains("[a-zA-Z]+") == false && Integer.parseInt(step.getText())<0)
		{
		step.setText("0");
		return;
		}
		int length = Integer.parseInt(step.getText());
		int clk = Integer.parseInt(clock.getText());
		if(clk-length<0)
			clk=0;
		else
			clk-=length;
		
		clock.setText(""+clk);
		updateTables(clk);
	}
	
	@FXML
	public void first(){
		
		updateTables(0);
		clock.setText("0");
	}
	
	public void updateTables(int clock)
	{
		
			missTextField.setText(""+Driver.instants.get(clock).miss);
			hitTextField.setText(""+Driver.instants.get(clock).hit);
			boolean miss=false;
			switch (Driver.instants.get(clock).state){//what current state (read(green-0),miss(red-1),wait(blue-2)
													//,replaced(orange-3),context switch-4,-waiting process-5)
			
			case 0://hit
					for (int i=0;i<Driver.memory.pIndex.length&&i<=127;i++)//update
					{
						p.get(i).setText(""+Driver.instants.get(clock).page[i]);
						l.get(i).setText(""+Driver.instants.get(clock).p[i]);
						p.get(i).setStyle("-fx-control-inner-background: WHITE");
						l.get(i).setStyle("-fx-control-inner-background: WHITE");
					}
					if(Driver.instants.get(clock).index<128){
					p.get(Driver.instants.get(clock).index).setStyle("-fx-control-inner-background: GREEN");
					l.get(Driver.instants.get(clock).index).setStyle("-fx-control-inner-background: GREEN");
					}
					state.setText("PID-"+Driver.instants.get(clock).runnningProcess+" got hit");
				break;
			case 1:
					miss=true;
					for (int i=0;i<Driver.memory.pIndex.length&&i<=127;i++)//update
					{

						p.get(i).setText(""+Driver.instants.get(clock).page[i]);
						l.get(i).setText(""+Driver.instants.get(clock).p[i]);
						p.get(i).setStyle("-fx-control-inner-background: WHITE");
						l.get(i).setStyle("-fx-control-inner-background: WHITE");
					}
					if(Driver.instants.get(clock).index<128){
					p.get(Driver.instants.get(clock).index).setStyle("-fx-control-inner-background: RED");
					l.get(Driver.instants.get(clock).index).setStyle("-fx-control-inner-background: RED");
					}
					state.setText("PID-"+Driver.instants.get(clock).runnningProcess+" got miss");
				break;
			case 2://waiting page
					for (int i=0;i<Driver.memory.pIndex.length&&i<=127;i++)//update
					{
						p.get(i).setText(""+Driver.instants.get(clock).page[i]);
						l.get(i).setText(""+Driver.instants.get(clock).p[i]);
						p.get(i).setStyle("-fx-control-inner-background: WHITE");
						l.get(i).setStyle("-fx-control-inner-background: WHITE");
					}
					state.setText("PID-"+Driver.instants.get(clock).runnningProcess+" is waiting page");
				break;
			case 3://replace
					for (int i=0;i<Driver.memory.pIndex.length&&i<=127;i++)//update
					{
						p.get(i).setText(""+Driver.instants.get(clock).page[i]);
						l.get(i).setText(""+Driver.instants.get(clock).p[i]);
						p.get(i).setStyle("-fx-control-inner-background: WHITE");
						l.get(i).setStyle("-fx-control-inner-background: WHITE");
					}
					if(Driver.instants.get(clock).index<128){
						p.get(Driver.instants.get(clock).index).setStyle("-fx-control-inner-background: ORANGE");
						l.get(Driver.instants.get(clock).index).setStyle("-fx-control-inner-background: ORANGE");
					}
					
					state.setText("PID-"+Driver.instants.get(clock).runnningProcess+": page replaced");
				break;
			case 4:
					for (int i=0;i<Driver.memory.pIndex.length&&i<=127;i++)//update
					{

						p.get(i).setStyle("-fx-control-inner-background: WHITE");
						l.get(i).setStyle("-fx-control-inner-background: WHITE");
					}
					state.setText("contextswitch "+Driver.instants.get(clock).runnningProcess);
				break;
			case 5:		
					for (int i=0;i<Driver.memory.pIndex.length&&i<=127;i++)//update
						{
						p.get(i).setText(""+Driver.instants.get(clock).page[i]);
						l.get(i).setText(""+Driver.instants.get(clock).p[i]);
						p.get(i).setStyle("-fx-control-inner-background: WHITE");
						l.get(i).setStyle("-fx-control-inner-background: WHITE");
					
						}
					state.setText("wait PID to arrive "+Driver.instants.get(clock).runnningProcess);
				break;
			default:
				break;
			}
			
			
			for(int i=0;i<Driver.instants.get(0).limit;i++)//re color off limit
			{
				p.get(i).setStyle("-fx-control-inner-background: BLACK");
				l.get(i).setStyle("-fx-control-inner-background: BLACK");
				p.get(i).setText("");
				l.get(i).setText("");
				
			}
			for(int i=Driver.instants.get(0).limit;i<Driver.memory.pIndex.length&&i<=127;i++)//re color not used
			{
				if(!Driver.instants.get(clock).used[i])
				{
					
					p.get(i).setStyle("-fx-control-inner-background: YELLOW");
					l.get(i).setStyle("-fx-control-inner-background: YELLOW");
					p.get(i).setText("");
					l.get(i).setText("");
					{
						if(miss&&Driver.instants.get(clock).index==i)
						{
							p.get(i).setStyle("-fx-control-inner-background: RED");
							l.get(i).setStyle("-fx-control-inner-background: RED");
						}
					}
				}
			}
			for(int i=Driver.memory.pIndex.length;i<=127;i++)
			{
				p.get(i).setStyle("-fx-control-inner-background: BLUE");
				l.get(i).setStyle("-fx-control-inner-background: BLUE");
				p.get(i).setText("");
				l.get(i).setText("");
			}
				
		
	}
	
	public void setFields()
	{
		p.add(p1);
		l.add(l1);
		p.add(p2);
		l.add(l2);
		p.add(p3);
		l.add(l3);
		p.add(p4);
		l.add(l4);
		p.add(p5);
		l.add(l5);
		p.add(p6);
		l.add(l6);
		p.add(p7);
		l.add(l7);
		p.add(p8);
		l.add(l8);
		p.add(p9);
		l.add(l9);
		p.add(p10);
		l.add(l10);
		p.add(p11);
		l.add(l11);
		p.add(p12);
		l.add(l12);
		p.add(p13);
		l.add(l13);
		p.add(p14);
		l.add(l14);
		p.add(p15);
		l.add(l15);
		p.add(p16);
		l.add(l16);
		p.add(p17);
		l.add(l17);
		p.add(p18);
		l.add(l18);
		p.add(p19);
		l.add(l19);
		p.add(p20);
		l.add(l20);
		p.add(p21);
		l.add(l21);
		p.add(p22);
		l.add(l22);
		p.add(p23);
		l.add(l23);
		p.add(p24);
		l.add(l24);
		p.add(p25);
		l.add(l25);
		p.add(p26);
		l.add(l26);
		p.add(p27);
		l.add(l27);
		p.add(p28);
		l.add(l28);
		p.add(p29);
		l.add(l29);
		p.add(p30);
		l.add(l30);
		p.add(p31);
		l.add(l31);
		p.add(p32);
		l.add(l32);
		p.add(p33);
		l.add(l33);
		p.add(p34);
		l.add(l34);
		p.add(p35);
		l.add(l35);
		p.add(p36);
		l.add(l36);
		p.add(p37);
		l.add(l37);
		p.add(p38);
		l.add(l38);
		p.add(p39);
		l.add(l39);
		p.add(p40);
		l.add(l40);
		p.add(p41);
		l.add(l41);
		p.add(p42);
		l.add(l42);
		p.add(p43);
		l.add(l43);
		p.add(p44);
		l.add(l44);
		p.add(p45);
		l.add(l45);
		p.add(p46);
		l.add(l46);
		p.add(p47);
		l.add(l47);
		p.add(p48);
		l.add(l48);
		p.add(p49);
		l.add(l49);
		p.add(p50);
		l.add(l50);
		p.add(p51);
		l.add(l51);
		p.add(p52);
		l.add(l52);
		p.add(p53);
		l.add(l53);
		p.add(p54);
		l.add(l54);
		p.add(p55);
		l.add(l55);
		p.add(p56);
		l.add(l56);
		p.add(p57);
		l.add(l57);
		p.add(p58);
		l.add(l58);
		p.add(p59);
		l.add(l59);
		p.add(p60);
		l.add(l60);
		p.add(p61);
		l.add(l61);
		p.add(p62);
		l.add(l62);
		p.add(p63);
		l.add(l63);
		p.add(p64);
		l.add(l64);
		p.add(p65);
		l.add(l65);
		p.add(p66);
		l.add(l66);
		p.add(p67);
		l.add(l67);
		p.add(p68);
		l.add(l68);
		p.add(p69);
		l.add(l69);
		p.add(p70);
		l.add(l70);
		p.add(p71);
		l.add(l71);
		p.add(p72);
		l.add(l72);
		p.add(p73);
		l.add(l73);
		p.add(p74);
		l.add(l74);
		p.add(p75);
		l.add(l75);
		p.add(p76);
		l.add(l76);
		p.add(p77);
		l.add(l77);
		p.add(p78);
		l.add(l78);
		p.add(p79);
		l.add(l79);
		p.add(p80);
		l.add(l80);
		p.add(p81);
		l.add(l81);
		p.add(p82);
		l.add(l82);
		p.add(p83);
		l.add(l83);
		p.add(p84);
		l.add(l84);
		p.add(p85);
		l.add(l85);
		p.add(p86);
		l.add(l86);
		p.add(p87);
		l.add(l87);
		p.add(p88);
		l.add(l88);
		p.add(p89);
		l.add(l89);
		p.add(p90);
		l.add(l90);
		p.add(p91);
		l.add(l91);
		p.add(p92);
		l.add(l92);
		p.add(p93);
		l.add(l93);
		p.add(p94);
		l.add(l94);
		p.add(p95);
		l.add(l95);
		p.add(p96);
		l.add(l96);
		p.add(p97);
		l.add(l97);
		p.add(p98);
		l.add(l98);
		p.add(p99);
		l.add(l99);
		p.add(p100);
		l.add(l100);
		p.add(p101);
		l.add(l101);
		p.add(p102);
		l.add(l102);
		p.add(p103);
		l.add(l103);
		p.add(p104);
		l.add(l104);
		p.add(p105);
		l.add(l105);
		p.add(p106);
		l.add(l106);
		p.add(p107);
		l.add(l107);
		p.add(p108);
		l.add(l108);
		p.add(p109);
		l.add(l109);
		p.add(p110);
		l.add(l110);
		p.add(p111);
		l.add(l111);
		p.add(p112);
		l.add(l112);
		p.add(p113);
		l.add(l113);
		p.add(p114);
		l.add(l114);
		p.add(p115);
		l.add(l115);
		p.add(p116);
		l.add(l116);
		p.add(p117);
		l.add(l117);
		p.add(p118);
		l.add(l118);
		p.add(p119);
		l.add(l119);
		p.add(p120);
		l.add(l120);
		p.add(p121);
		l.add(l121);
		p.add(p122);
		l.add(l122);
		p.add(p123);
		l.add(l123);
		p.add(p124);
		l.add(l124);
		p.add(p125);
		l.add(l125);
		p.add(p126);
		l.add(l126);
		p.add(p127);
		l.add(l127);
		p.add(p128);
		l.add(l128);
		
		for(int i=0;i<128;i++)
		{
			p.get(i).setStyle("-fx-font: 8px Tahoma");
			l.get(i).setStyle("-fx-font: 8px Tahoma");
		}
	}
	
	
	private ArrayList<TextField> p = new ArrayList<>();
	private ArrayList<TextField> l = new ArrayList<>();
	
	
	@FXML
	private Button done,begin,next,last,back,first;
	
	@FXML
	private TextField step,clock,state,missTextField,hitTextField;
	
	@FXML
	private Label missLabel,hitLabel,clockLabel;
	
	@FXML
	private TextField p1;

	@FXML
	private TextField l1;

	@FXML
	private TextField p2;

	@FXML
	private TextField l2;

	@FXML
	private TextField p3;

	@FXML
	private TextField l3;

	@FXML
	private TextField p4;

	@FXML
	private TextField l4;

	@FXML
	private TextField p5;

	@FXML
	private TextField l5;

	@FXML
	private TextField p6;

	@FXML
	private TextField l6;

	@FXML
	private TextField p7;

	@FXML
	private TextField l7;

	@FXML
	private TextField p8;

	@FXML
	private TextField l8;

	@FXML
	private TextField p9;

	@FXML
	private TextField l9;

	@FXML
	private TextField p10;

	@FXML
	private TextField l10;

	@FXML
	private TextField p11;

	@FXML
	private TextField l11;

	@FXML
	private TextField p12;

	@FXML
	private TextField l12;

	@FXML
	private TextField p13;

	@FXML
	private TextField l13;

	@FXML
	private TextField p14;

	@FXML
	private TextField l14;

	@FXML
	private TextField p15;

	@FXML
	private TextField l15;

	@FXML
	private TextField p16;

	@FXML
	private TextField l16;

	@FXML
	private TextField p17;

	@FXML
	private TextField l17;

	@FXML
	private TextField p18;

	@FXML
	private TextField l18;

	@FXML
	private TextField p19;

	@FXML
	private TextField l19;

	@FXML
	private TextField p20;

	@FXML
	private TextField l20;

	@FXML
	private TextField p21;

	@FXML
	private TextField l21;

	@FXML
	private TextField p22;

	@FXML
	private TextField l22;

	@FXML
	private TextField p23;

	@FXML
	private TextField l23;

	@FXML
	private TextField p24;

	@FXML
	private TextField l24;

	@FXML
	private TextField p25;

	@FXML
	private TextField l25;

	@FXML
	private TextField p26;

	@FXML
	private TextField l26;

	@FXML
	private TextField p27;

	@FXML
	private TextField l27;

	@FXML
	private TextField p28;

	@FXML
	private TextField l28;

	@FXML
	private TextField p29;

	@FXML
	private TextField l29;

	@FXML
	private TextField p30;

	@FXML
	private TextField l30;

	@FXML
	private TextField p31;

	@FXML
	private TextField l31;

	@FXML
	private TextField p32;

	@FXML
	private TextField l32;

	@FXML
	private TextField p33;

	@FXML
	private TextField l33;

	@FXML
	private TextField p34;

	@FXML
	private TextField l34;

	@FXML
	private TextField p35;

	@FXML
	private TextField l35;

	@FXML
	private TextField p36;

	@FXML
	private TextField l36;

	@FXML
	private TextField p37;

	@FXML
	private TextField l37;

	@FXML
	private TextField p38;

	@FXML
	private TextField l38;

	@FXML
	private TextField p39;

	@FXML
	private TextField l39;

	@FXML
	private TextField p40;

	@FXML
	private TextField l40;

	@FXML
	private TextField p41;

	@FXML
	private TextField l41;

	@FXML
	private TextField p42;

	@FXML
	private TextField l42;

	@FXML
	private TextField p43;

	@FXML
	private TextField l43;

	@FXML
	private TextField p44;

	@FXML
	private TextField l44;

	@FXML
	private TextField p45;

	@FXML
	private TextField l45;

	@FXML
	private TextField p46;

	@FXML
	private TextField l46;

	@FXML
	private TextField p47;

	@FXML
	private TextField l47;

	@FXML
	private TextField p48;

	@FXML
	private TextField l48;

	@FXML
	private TextField p49;

	@FXML
	private TextField l49;

	@FXML
	private TextField p50;

	@FXML
	private TextField l50;

	@FXML
	private TextField p51;

	@FXML
	private TextField l51;

	@FXML
	private TextField p52;

	@FXML
	private TextField l52;

	@FXML
	private TextField p53;

	@FXML
	private TextField l53;

	@FXML
	private TextField p54;

	@FXML
	private TextField l54;

	@FXML
	private TextField p55;

	@FXML
	private TextField l55;

	@FXML
	private TextField p56;

	@FXML
	private TextField l56;

	@FXML
	private TextField p57;

	@FXML
	private TextField l57;

	@FXML
	private TextField p58;

	@FXML
	private TextField l58;

	@FXML
	private TextField p59;

	@FXML
	private TextField l59;

	@FXML
	private TextField p60;

	@FXML
	private TextField l60;

	@FXML
	private TextField p61;

	@FXML
	private TextField l61;

	@FXML
	private TextField p62;

	@FXML
	private TextField l62;

	@FXML
	private TextField p63;

	@FXML
	private TextField l63;

	@FXML
	private TextField p64;

	@FXML
	private TextField l64;

	@FXML
	private TextField p65;

	@FXML
	private TextField l65;

	@FXML
	private TextField p66;

	@FXML
	private TextField l66;

	@FXML
	private TextField p67;

	@FXML
	private TextField l67;

	@FXML
	private TextField p68;

	@FXML
	private TextField l68;

	@FXML
	private TextField p69;

	@FXML
	private TextField l69;

	@FXML
	private TextField p70;

	@FXML
	private TextField l70;

	@FXML
	private TextField p71;

	@FXML
	private TextField l71;

	@FXML
	private TextField p72;

	@FXML
	private TextField l72;

	@FXML
	private TextField p73;

	@FXML
	private TextField l73;

	@FXML
	private TextField p74;

	@FXML
	private TextField l74;

	@FXML
	private TextField p75;

	@FXML
	private TextField l75;

	@FXML
	private TextField p76;

	@FXML
	private TextField l76;

	@FXML
	private TextField p77;

	@FXML
	private TextField l77;

	@FXML
	private TextField p78;

	@FXML
	private TextField l78;

	@FXML
	private TextField p79;

	@FXML
	private TextField l79;

	@FXML
	private TextField p80;

	@FXML
	private TextField l80;

	@FXML
	private TextField p81;

	@FXML
	private TextField l81;

	@FXML
	private TextField p82;

	@FXML
	private TextField l82;

	@FXML
	private TextField p83;

	@FXML
	private TextField l83;

	@FXML
	private TextField p84;

	@FXML
	private TextField l84;

	@FXML
	private TextField p85;

	@FXML
	private TextField l85;

	@FXML
	private TextField p86;

	@FXML
	private TextField l86;

	@FXML
	private TextField p87;

	@FXML
	private TextField l87;

	@FXML
	private TextField p88;

	@FXML
	private TextField l88;

	@FXML
	private TextField p89;

	@FXML
	private TextField l89;

	@FXML
	private TextField p90;

	@FXML
	private TextField l90;

	@FXML
	private TextField p91;

	@FXML
	private TextField l91;

	@FXML
	private TextField p92;

	@FXML
	private TextField l92;

	@FXML
	private TextField p93;

	@FXML
	private TextField l93;

	@FXML
	private TextField p94;

	@FXML
	private TextField l94;

	@FXML
	private TextField p95;

	@FXML
	private TextField l95;

	@FXML
	private TextField p96;

	@FXML
	private TextField l96;

	@FXML
	private TextField p97;

	@FXML
	private TextField l97;

	@FXML
	private TextField p98;

	@FXML
	private TextField l98;

	@FXML
	private TextField p99;

	@FXML
	private TextField l99;

	@FXML
	private TextField p100;

	@FXML
	private TextField l100;

	@FXML
	private TextField p101;

	@FXML
	private TextField l101;

	@FXML
	private TextField p102;

	@FXML
	private TextField l102;

	@FXML
	private TextField p103;

	@FXML
	private TextField l103;

	@FXML
	private TextField p104;

	@FXML
	private TextField l104;

	@FXML
	private TextField p105;

	@FXML
	private TextField l105;

	@FXML
	private TextField p106;

	@FXML
	private TextField l106;

	@FXML
	private TextField p107;

	@FXML
	private TextField l107;

	@FXML
	private TextField p108;

	@FXML
	private TextField l108;

	@FXML
	private TextField p109;

	@FXML
	private TextField l109;

	@FXML
	private TextField p110;

	@FXML
	private TextField l110;

	@FXML
	private TextField p111;

	@FXML
	private TextField l111;

	@FXML
	private TextField p112;

	@FXML
	private TextField l112;

	@FXML
	private TextField p113;

	@FXML
	private TextField l113;

	@FXML
	private TextField p114;

	@FXML
	private TextField l114;

	@FXML
	private TextField p115;

	@FXML
	private TextField l115;

	@FXML
	private TextField p116;

	@FXML
	private TextField l116;

	@FXML
	private TextField p117;

	@FXML
	private TextField l117;

	@FXML
	private TextField p118;

	@FXML
	private TextField l118;

	@FXML
	private TextField p119;

	@FXML
	private TextField l119;

	@FXML
	private TextField p120;

	@FXML
	private TextField l120;

	@FXML
	private TextField p121;

	@FXML
	private TextField l121;

	@FXML
	private TextField p122;

	@FXML
	private TextField l122;

	@FXML
	private TextField p123;

	@FXML
	private TextField l123;

	@FXML
	private TextField p124;

	@FXML
	private TextField l124;

	@FXML
	private TextField p125;

	@FXML
	private TextField l125;

	@FXML
	private TextField p126;

	@FXML
	private TextField l126;

	@FXML
	private TextField p127;

	@FXML
	private TextField l127;

	@FXML
	private TextField p128;

	@FXML
	private TextField l128;

}
