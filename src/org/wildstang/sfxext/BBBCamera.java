package org.wildstang.sfxext;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Ellipse;
import dashfx.controls.DataAnchorPane;
import dashfx.lib.controls.Category;
import dashfx.lib.controls.DashFXProperties;
import dashfx.lib.controls.Designable;
import dashfx.lib.controls.GroupType;
import dashfx.lib.data.DataCoreProvider;
import dashfx.lib.data.DataPaneMode;
import dashfx.lib.data.SmartValue;

// Add FXML required annotations
// Add our extension to the "Custom" toolbox
@Category("Custom")
@Designable(value = "Beaglebone Camera Feed", description = "A Control to display an image sent from the Beaglebone")
@GroupType(value = "BBBCamera")
@DashFXProperties("Sealed: true, Save Children: false")
public class BBBCamera extends DataAnchorPane
{
   private SmartValue imgCount;
   private SmartValue imgurlsv;
   private ImageView iv;
   private Ellipse el;

   @SuppressWarnings("restriction")
   public BBBCamera()
   {
      setDataMode(DataPaneMode.Nested);
      iv = new ImageView();

      this.getChildren().add(iv);
      nameProperty().addListener(new ChangeListener<String>()
      {
         @Override
         public void changed(ObservableValue<? extends String> ov, String t,
               String t1)
         {
            unwatch();
            try
            {
               imgCount = getObservable("count");
               imgurlsv = getObservable("url");
               rewatch();
            }
            catch (NullPointerException n)
            {
               // fail, ignore, as we must not be registered yet
            }
         }
      });
   }

   @Override
   public void registered(DataCoreProvider provider)
   {
      // TODO Auto-generated method stub
      super.registered(provider);
      this.unwatch();
      // if we are being registered, then we can finally get
      // the img variable
      // otherwise just unwatch as we are being unregistered
      if (provider != null)
      {
         imgCount = getObservable("count");
         imgurlsv = getObservable("url");
         rewatch();
      }

   }

   private void rewatch()
   {
      imgCount.addListener(reloadImg);
   }

   private void unwatch()
   {
      // this function un-binds all the variable
      if (imgCount != null)
      {
         imgCount.removeListener(reloadImg);
      }
   }

   private ChangeListener reloadImg = new ChangeListener<Object>()
   {
      @Override
      public void changed(ObservableValue<? extends Object> ov, Object t,
            Object t1)
      {
         String imgurl = imgurlsv.getData().asString();

         if (!imgurl.isEmpty())
         {
            iv.setImage(new Image(imgurl));
         }

      }
   };

}
