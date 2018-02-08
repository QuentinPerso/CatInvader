package quentin.jeu.cat.desktop;


import quentin.jeu.cat.Shop;
import quentin.jeu.cat.ui.MapUI;
import quentin.jeu.cat.utils.Save;

public class DeskShop implements Shop{
	
	public DeskShop(){
		
		/*// compute your public key and store it in base64EncodedPublicKey
		String test ="MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAzetvZharEasbeLVswOBDz92HL0qxwKqs0DBCeH6QA+Q2KpCzQdX+i64aa0LRMcVOnZBAEnZtkqQ1AqdJUC8m0nitCA2E0UhflJtHJOvFACE+DsEXTaiPruz7mvzoFVEL/djFEYp+wBDL+7rmf3O4qVKnqsK3myF7oEQzEpkayCHdZn0RFFqE8dKn2vwIXdFK34R+5q5m6pLvmKg70yA7ROrWbHac3U9R4b+cWdm8mCqL2qxXH+p7tZqNB9gimGP3Qgrd2pd1Tte8yI+w6Zrk2e86JZmfWIrArbYZFlFGx4LoxFUaJ9QZy7wkCv5tSnn2oCvqHyEE1exTHkU2dxaJMQIDAQAB";
		String base64EncodedPublicKey =  DecrementEachletter(Splash.piece1)
				+ Charactertt.piece2 
				+ Tuto.piece2 
				+ Intro.piece1 
				+ Intro.piece5 
				+ "4LoxFUaJ9QZy7wkCv5tSnn2oCvqHyEE1exTHkU2dxaJMQIDAQAB";
			System.out.println(base64EncodedPublicKey);*/
	}
	
	/*private String DecrementEachletter(String string) {
		String output = null;
		String value = string;
		for(int i=0; i<string.length();i++){
			int charValue = value.charAt(i);
			String next = String.valueOf( (char) (charValue - 1));
			output=output+next;
			
		}
		output= output.substring(4, string.length()+4);
		return output;
	}*/
	
	@Override
	public void buyMaki1() {
		MapUI.maki1buy=true;
		Save.gd.maki+=15;
		Save.save();
	}

	@Override
	public void buyMaki2() {
		MapUI.maki2buy=true;
		Save.gd.maki+=40;
		Save.save();
	}
	
	@Override
	public void buymetal() {
		MapUI.metalbuy=true;
		Save.gd.metal+=20;
		Save.save();
		
	}
	
	@Override
	public void checkprice() {
		MapUI.price1 =
                "0,8 €";
        MapUI.price2 =
                "1,5 €";
        MapUI.price3 =
                "1,5 €";
		
	}

}
