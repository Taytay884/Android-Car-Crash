<!-- activity_main.xml -->
<GridLayout
android:id="@+id/gridLayout"
android:layout_width="match_parent"
android:layout_height="match_parent"
android:columnCount="3"
android:rowCount="7">

<!-- Placeholder cells for lanes and obstacles will be dynamically added -->

</GridLayout>

<LinearLayout
android:layout_width="match_parent"
android:layout_height="wrap_content"
android:orientation="horizontal"
android:layout_gravity="bottom|center">

<Button
android:id="@+id/btnLeft"
android:layout_width="wrap_content"
android:layout_height="wrap_content"
android:text="Left" />

<Button
android:id="@+id/btnRight"
android:layout_width="wrap_content"
android:layout_height="wrap_content"
android:text="Right" />

</LinearLayout>
