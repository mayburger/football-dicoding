package com.mayburger.football.utils

import android.graphics.Typeface
import android.widget.TextView

/**
* Created by Mayburger on 9/1/18.
*/
/**
 * Created by Mayburger on 9/1/18.
 */
val TextView.black: Typeface get() = Typeface.createFromAsset(context.assets, "fonts/Poppins-Black.otf")
val TextView.bold: Typeface get() = Typeface.createFromAsset(context.assets, "fonts/Poppins-Bold.otf")
val TextView.medium: Typeface get() = Typeface.createFromAsset(context.assets, "fonts/Poppins-Medium.otf")
val TextView.regular: Typeface get() = Typeface.createFromAsset(context.assets, "fonts/Poppins-Regular.otf")
val TextView.semibold: Typeface get() = Typeface.createFromAsset(context.assets, "fonts/Poppins-SemiBold.otf")
val TextView.light: Typeface get() = Typeface.createFromAsset(context.assets, "fonts/Poppins-Light.otf")
val TextView.quickbold: Typeface get() = Typeface.createFromAsset(context.assets, "fonts/quicksand bold.otf")
val TextView.quickbook: Typeface get() = Typeface.createFromAsset(context.assets, "fonts/quicksand book.otf")
val TextView.quicklight: Typeface get() = Typeface.createFromAsset(context.assets, "fonts/quicksand light.otf")


