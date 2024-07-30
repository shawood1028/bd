#!/usr/bin/python
# -*- coding: UTF-8 -*-

from sqlalchemy import create_engine
from sqlalchemy.orm import sessionmaker
from sqlalchemy.ext.declarative import declarative_base
from sqlalchemy import Column
from sqlalchemy.dialects import mysql
import time
import random

from datetime import datetime

DB_URL = "mysql+pymysql://root:abcd1234!@localhost/test-cdc?charset=utf8"

# 声明所有ORM类对象继承的基类
BaseModel = declarative_base()

class User(BaseModel):
    __tablename__ = "t_table_01"
    id = Column(mysql.BIGINT(unsigned=True), primary_key=True, autoincrement=True)
    coin_num = Column(mysql.BIGINT(unsigned=True), nullable=False)



if __name__ == '__main__':
    engine = create_engine(DB_URL,
                       echo=True,
                       pool_size=8,
                       pool_recycle=60*30
                       )
    # 创建 DBSession 类型
    DBSession = sessionmaker(bind=engine)
    # 创建 session 对象
    session = DBSession()

    
    # 批量生成数据1w条
    s_time = time.time()
    session.add(User(coin_num=123789))
    session.bulk_save_objects(
        [
            User(coin_num=i)
            for i in range(1)
        ]
    )
    session.commit()
    session.close()
    e_time = time.time()
    # 耗费时间
    u_time = e_time-s_time
    print(f'{u_time} s')
