# Add project specific ProGuard rules here.

# Firebase
-keepattributes Signature
-keepattributes *Annotation*
-keepattributes EnclosingMethod
-keepattributes InnerClasses

# Keep data model classes
-keep class com.schoolbus.common.models.** { *; }

# Google Maps
-keep class com.google.android.gms.maps.** { *; }
-keep interface com.google.android.gms.maps.** { *; }

# ZXing QR Scanner
-keep class com.google.zxing.** { *; }
-dontwarn com.google.zxing.**
