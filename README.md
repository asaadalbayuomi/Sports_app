# تطبيق تمارين — Sports App

تطبيق أندرويد لإدارة قائمة تمارين رياضية، مع تخزين محلي في قاعدة بيانات SQLite.

## المميزات

- **إضافة تمرين**: الاسم (إجباري)، المدة بالدقائق (إجباري)، المجموعة العضلية (اختياري)، وحالة «تم».
- **عرض القائمة**: مرتبة أبجدياً حسب اسم التمرين.
- **بحث فوري**: يفلتر النتائج أثناء الكتابة.
- **تعليم كـ «تم»**: عبر الـ CheckBox، ويُحفظ مباشرة في قاعدة البيانات.
- **حذف**: بالضغط المطوّل على العنصر، مع رسالة تأكيد.

## التقنيات

| العنصر | التفاصيل |
|---|---|
| اللغة | Java |
| أقل إصدار (minSdk) | 24 — Android 7.0 |
| إصدار الهدف (targetSdk) | 37 |
| الواجهات | RecyclerView + Adapter، Material 3 |
| التخزين | SQLite عبر `SQLiteOpenHelper` |

## بنية المشروع

```
app/src/main/
├── java/com/example/mytype/
│   ├── MainActivity.java      # الواجهة الرئيسية + منطق الإضافة/البحث/الحذف
│   ├── ExerciseAdapter.java   # ربط قائمة التمارين بـ RecyclerView
│   ├── Exercise.java          # نموذج بيانات التمرين (POJO)
│   └── DBHelper.java          # إنشاء قاعدة البيانات وعمليات CRUD
├── res/
│   ├── layout/activity_main.xml   # شاشة الإدخال + القائمة
│   ├── layout/activity_item.xml   # تصميم الصف الواحد
│   └── values/strings.xml         # كل النصوص
└── AndroidManifest.xml
```

## جدول قاعدة البيانات

جدول `exercises`:

| العمود | النوع | ملاحظات |
|---|---|---|
| `id` | INTEGER | PRIMARY KEY AUTOINCREMENT |
| `name` | TEXT | NOT NULL |
| `duration` | INTEGER | بالدقائق |
| `completed` | INTEGER | 0 / 1 |
| `muscle_group` | TEXT | اختياري |

## التشغيل

1. افتح المشروع في Android Studio.
2. انتظر انتهاء Gradle Sync.
3. اضغط **Run ▶** على محاكي أو جهاز حقيقي.

## لقطات الشاشة

> أضف هنا صور الشاشة: `docs/screenshot-main.png`

---

**المقرر:** تطوير تطبيقات الأندرويد — المشروع العملي.
