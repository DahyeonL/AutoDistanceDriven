package co.kr.daou.autodistancedriven.di


import androidx.room.Room
import co.kr.daou.autodistancedriven.model.db.DrivingDatabase
import co.kr.daou.autodistancedriven.model.db.repository.DrivingRecordRepository
import org.koin.dsl.module

//기술 스택 #1 Koin
val databaseModule = module {
    single {
        Room.databaseBuilder(get(), DrivingDatabase::class.java,"driving_database")
            //IllegalStateException - 이전 경로 누락시 발생
            //발생 시 기존데이터 전부 삭제 후 테이블 재생성
            .fallbackToDestructiveMigration()
            .build()
        //콜백사용하면 처음 데이터 셋팅가능
    }
    single {
        get<DrivingDatabase>().drivingRecordDao()
    }
}
val repositoryModule = module {
    single { DrivingRecordRepository(get()) }
}
