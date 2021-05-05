分为总任务,我的一天,清单三类

任务可以通过checkbox来标记完成，也可以到期自动完成

 suspend fun observeTasks(): LiveData<List<Task>> {
        lateinit var i : LiveData<List<Task>>
        coroutineScope { 
            launch { 
                i = database.taskDao.observeTasks()
            }
        }
        return i
    }
    
    ###不要碰transformations.map 太坑!!
