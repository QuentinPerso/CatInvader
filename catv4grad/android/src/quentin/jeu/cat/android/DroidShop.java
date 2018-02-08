package quentin.jeu.cat.android;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;

import com.android.vending.billing.IabHelper;
import com.android.vending.billing.IabResult;
import com.android.vending.billing.Inventory;
import com.android.vending.billing.Purchase;

import quentin.jeu.cat.Shop;
import quentin.jeu.cat.ui.MapUI;
import quentin.jeu.cat.utils.Save;

public class DroidShop implements Shop{
	
	IabHelper mHelper;
	private Activity context;
	public  String SKU_MAKI5  = "5maki";
	public  String SKU_MAKI15 = "15maki";
	public  String SKU_METAL  = "metal";
	private String price1, price2, price3;
	
	public DroidShop(Activity context){
		this.context=context;

		//List of IAP
		final List<String> additionalSkuList = new ArrayList<String>();
        additionalSkuList.add(SKU_MAKI5);
        additionalSkuList.add(SKU_MAKI15);
        additionalSkuList.add(SKU_METAL);
        
        //Initializing connection
        mHelper = new IabHelper(context, "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAzetvZharEasbeLVswOBDz92HL0qxwKqs0DBCeH6QA+Q2KpCzQdX+i64aa0LRMcVOnZBAEnZtkqQ1AqdJUC8m0nitCA2E0UhflJtHJOvFACE+DsEXTaiPruz7mvzoFVEL/djFEYp+wBDL+7rmf3O4qVKnqsK3myF7oEQzEpkayCHdZn0RFFqE8dKn2vwIXdFK34R+5q5m6pLvmKg70yA7ROrWbHac3U9R4b+cWdm8mCqL2qxXH+p7tZqNB9gimGP3Qgrd2pd1Tte8yI+w6Zrk2e86JZmfWIrArbYZFlFGx4LoxFUaJ9QZy7wkCv5tSnn2oCvqHyEE1exTHkU2dxaJMQIDAQAB");
        mHelper.startSetup(new IabHelper.OnIabSetupFinishedListener() {
			@Override
			public void onIabSetupFinished(IabResult result) {
				 if (!result.isSuccess()) {
			        // Oh noes, there was a problem.
			        System.out.println("problem");
			      }
				// Hooray, IAB is fully set up!
				System.out.println("setupfinished");
			    
				//then check purchase
				mHelper.queryInventoryAsync(true, additionalSkuList,mGotInventoryListener);
			   }
        });
	}

	/*private String DecrementEachletter(String string) {
		String output = null;
		String value = string;
		for(int i=0; i<string.length();i++){
			int charValue = value.charAt(i);
			String next = String.valueOf( (char) (charValue - 1));
			output=output+next;
			
		}
		output= output.substring(4, string.length());
		return output;
	}*/

	@Override
	public void buyMaki1() {
     mHelper.launchPurchaseFlow(context, SKU_MAKI5, 10001,   
     		   mPurchaseFinishedListener, "bGoa+V7g/yqDXvKRqq+JTFn4uQZbPiQJo4pf9RzJ");
		
	}

	@Override
	public void buyMaki2() {
		 mHelper.launchPurchaseFlow(context, SKU_MAKI15, 10001,   
	     		   mPurchaseFinishedListener, "bGoa+V7g/yqDXvKRqq+JTFn4uQZbPiQJo4pf9RzJ");
		
	}
	@Override
	public void buymetal() {
		mHelper.launchPurchaseFlow(context, SKU_METAL, 10001,   
	     		   mPurchaseFinishedListener, "bGoa+V7g/yqDXvKRqq+JTFn4uQZbPiQJo4pf9RzJ");
		
	}
	
	@Override
	public void checkprice() {
		MapUI.price1=price1;
		MapUI.price2=price2;
		MapUI.price3=price3;
		
	}
	
	// Listener that's called when we finish querying the items and subscriptions we own
    IabHelper.QueryInventoryFinishedListener mGotInventoryListener = new IabHelper.QueryInventoryFinishedListener() {
		@Override
		public void onQueryInventoryFinished(IabResult result, Inventory inventory) {

            // Have we been disposed of in the meantime? If so, quit.
            if (mHelper == null) return;

            // Is it a failure?
            if (result.isFailure()) {
              // System.out.println("Failed to query inventory: " + result);
                return;
            }

           // System.out.println("Query inventory was successful.");

            /*
             * Check for items we own. Notice that for each purchase, we check
             * the developer payload to see if it's correct! See
             * verifyDeveloperPayload().
             */

            // Do we have the premium upgrade?
            price1 = inventory.getSkuDetails(SKU_MAKI5) .getPrice();
            price2 = inventory.getSkuDetails(SKU_MAKI15).getPrice();
            price3 = inventory.getSkuDetails(SKU_METAL).getPrice();


            // Check for gas delivery -- if we own gas, we should fill up the tank immediately
            Purchase MakiPurchase = inventory.getPurchase(SKU_MAKI5);
            if (MakiPurchase != null && verifyDeveloperPayload(MakiPurchase)) {
                mHelper.consumeAsync(inventory.getPurchase(SKU_MAKI5), mConsumeFinishedListener);
                return;
            }
		}
    };
    
    // Listener that's called when we finish purchasing the items 
	IabHelper.OnIabPurchaseFinishedListener mPurchaseFinishedListener 
 	= new IabHelper.OnIabPurchaseFinishedListener() {
		 @Override
		 public void onIabPurchaseFinished(IabResult result, Purchase purchase) {
			 if (result.isFailure()) {
				 System.out.println("Error purchasing: " + result);
				 return;
			 }      
			 else if (purchase.getSku().equals(SKU_MAKI5)) {
				 mHelper.consumeAsync(purchase, mConsumeFinishedListener);
			 }
			 else if (purchase.getSku().equals(SKU_MAKI15)) {
				 mHelper.consumeAsync(purchase, mConsumeFinishedListener);
			 }
			 else if (purchase.getSku().equals(SKU_METAL)) {
				 mHelper.consumeAsync(purchase, mConsumeFinishedListener);
			 }
		}
	};
	
	
	// Listener that's called when we finish consuming consumable items 
	IabHelper.OnConsumeFinishedListener mConsumeFinishedListener = new IabHelper.OnConsumeFinishedListener() {
    	@Override
    	public void onConsumeFinished(Purchase purchase, IabResult result) {
            // if we were disposed of in the meantime, quit.
            if (mHelper == null) return;

            // We know this is the "gas" sku because it's the only one we consume,
            // so we don't check which sku was consumed. If you have more than one
            // sku, you probably should check...
            if (result.isSuccess()) {
                // successfully consumed, so we apply the effects of the item in our
                // game world's logic, which in our case means filling the gas tank a bit
             if (purchase.getSku().equals(SKU_MAKI5)) {
            	 Save.gd.maki+=15;
                 Save.save();
                 MapUI.maki1buy=true;
   			 }
   			 else if (purchase.getSku().equals(SKU_MAKI15)) {
   				Save.gd.maki+=40;
                Save.save();
                MapUI.maki2buy=true;
   			 }
   			 else if (purchase.getSku().equals(SKU_METAL)) {
   				Save.gd.metal+=20;
                Save.save();
                MapUI.metalbuy=true;
   			 }
               // alert("You filled 1/4 tank. Your tank is now " + String.valueOf(mTank) + "/4 full!");
            }
            else {
                //complain("Error while consuming: " + result);
            }
           // updateUi();
          //  setWaitScreen(false);
        }
		
    };
	
	boolean verifyDeveloperPayload(Purchase p) {
	        //String payload = p.getDeveloperPayload();

	       /* 
	         * verify that the developer payload of the purchase is correct. It will be
	         * the same one that you sent when initiating the purchase.
	         *
	         * WARNING: Locally generating a random string when starting a purchase and
	         * verifying it here might seem like a good approach, but this will fail in the
	         * case where the user purchases an item on one device and then uses your app on
	         * a different device, because on the other device you will not have access to the
	         * random string you originally generated.
	         *
	         * So a good developer payload has these characteristics:
	         *
	         * 1. If two different users purchase an item, the payload is different between them,
	         *    so that one user's purchase can't be replayed to another user.
	         *
	         * 2. The payload must be such that you can verify it even when the app wasn't the
	         *    one who initiated the purchase flow (so that items purchased by the user on
	         *    one device work on other devices owned by the user).
	         *
	         * Using your own server to store and verify developer payloads across app
	         * installations is recommended.
	         */

	        return true;
	    }

}
