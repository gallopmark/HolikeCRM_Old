package com.holike.imagepicker.view;


import com.holike.imagepicker.bean.ImageFolder;

import java.util.ArrayList;

public interface ImagePickerView extends BaseView{

    void onInitImageList();

    void onCheckExternalPermission();

    void onLoadFolders(ArrayList<ImageFolder> folders);

}
