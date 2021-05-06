# Android记事本-TodoManager

### 红岩网校移动开发部 2020级 中期考核

### **App的简要介绍**
​		<u>Android记事本应用，主要涵盖日程查看/日程分类/日程到期提醒/我的一天功能，实现了绝大部分功能。</u>

​		**首次进入时请允许后台权限。权限部分保证app在后台时也能够推送通知。权限仅用于推送通知。**

#### 功能介绍：

>1.日程增加/删除/查看/修改功能：
>
>​		进入首页即可查看日程，该页面列出所有待办日程，点击每项日程可进入对应的日程界面，可以更改日程。长按每项日程弹出提示删除日程，可以单击确定删除日程。单击FAB即可添加日程，按照提示操作。增加成功会有提示反馈。
>
>​		将按钮打√，可以将事件标为完成。完成的事项将不会提示。
>
>2.我的一天
>
>​		在我的一天中，可以查看当前24小时内的待办事项。事项按距离现在的时间远近排列，近一点的待办事项排在前面。
>
>3.任务清单
>
>​		任务清单中列出被分类的几项任务类别。单击任务类别可以回到日程查看上去。由于时间因素，目前任务类别所包含的任务尚不可查看。
>
>​		可以新建任务清单，点击FAB按照提示操作即可

#### 技术设计及实现思路：

><u>使用完全体MVVM架构</u>：
>
>​		包含 Entity / Dao / RoomDatabase / Repository
>
>​		Repository 采用Kotlin的携程处理更改，这样就不用传统的多线程操作
>
>```
>suspend fun saveTask(task: Task) {
>	coroutineScope {
>  	launch {
>     		 database.taskDao.insertTask(task)
>  	}
>  }
>}
>```
>
>​		数据库使用Room来完成。获取数据库采用RoomDatabase中的如下方法。注意：整个app只设计了一个RoomDatabase。
>
>```
>//获取数据库单例，线程安全
>fun getInstance(context: Context): TaskRoomDatabase {
>  synchronized(this) {
>      var instance = INSTANCE
>
>      // TODO 如果数据库为空，那么就新建一个数据库实例，不允许数据库转移储存
>      if (instance == null) {
>          instance = Room.databaseBuilder(
>                  context.applicationContext,
>                  TaskRoomDatabase::class.java,
>                  "task_database")
>                  .fallbackToDestructiveMigration()
>                  .build()
>          // 将INSTANCE分配给新创建的数据库。
>          INSTANCE = instance
>      	}
> 	// 返回一个数据库实例
>      	return instance
>     	}
>  }
>```
>
>viewModel中操作数据库：
>
>```
>private fun createTask(task: Task) {
>	viewModelScope.launch {
>		tasksRepository.saveTask(task)
>  	}
>}
>```
>
><u>使用LiveData自动更新视图，并在视图中进行数据绑定</u>：
>
>​		下面示例进行在layout.xml中进行数据绑定
>
>```
><data>
> 
>   <variable
>            name="viewmodel"
>            type="com.example.todomanagement.ui.add.AddViewModel" />
>     </data>
> 
>//在视图中操作
>android:text="@={viewmodel.description}"
>app:onLongClick_category="@{category}"
>android:onClick="@{() -> viewmodel.openOverview(category.id)}"
>```
>​		设立ViewModel和填充数据
>
>```
>//初始化view model
>viewModel = ViewModelProvider(this,
>AddViewModelFactory(requireNotNull(this.activity).application))
>        .get(AddViewModel::class.java)
>        
>val binding: FragmentAddBinding = DataBindingUtil
>        .inflate(inflater, R.layout.fragment_add, container, false)
>        
>// 设置life cycle owner
>binding.lifecycleOwner = this.viewLifecycleOwner
>binding.viewmodel = this.viewModel
>```
>​		对于系统没有的绑定方法，可以自定义bindingAdapter进行绑定
>```
>@RequiresApi(Build.VERSION_CODES.R)
>@BindingAdapter("app:onLongClick_task")
>fun LinearLayout.setOnLongClickListener(item: Task) {
>	setOnLongClickListener {
>		it.findFragment<OverviewFragment>().deleteTask(item)
>  		return@setOnLongClickListener true
>	}
>}
>```

><u>其他技术诸如导航/date time picker控件的使用/Kotlin 语言 不过多叙述</u>：

### **心得体会**

​		**书山有路勤为径，学海无涯苦作舟**



下一步努力方向：巩固/解耦合/模块化 更好的理解计算机和软件





