-- ------------------- (文件表) ---------------------

CREATE TABLE [t_file](
  [FileId] UNIQUEIDENTIFIER PRIMARY KEY NOT NULL UNIQUE, --文件Id
  [FileName] VARCHAR(100), --文件名
  [IsFolder] INT NOT NULL DEFAULT 0, -- 是否是文件夹，0：不是（默认），1：是
  [FilePath] VARCHAR(200), -- 文件路径
  [IsDel] INT NOT NULL DEFAULT 0, -- 是否删除，0：不是（默认），1：是
  [CreateUserId] UNIQUEIDENTIFIER, -- 创建用户Id
  [CreateDate] TIMESTAMP NOT NULL DEFAULT (DATETIME ()), -- 创建日期
  [UpdateDate] TIMESTAMP NOT NULL DEFAULT (DATETIME ()), -- 更新日期
  [MD5] varchar(50), -- 文件MD5值
  [FileRemark] VARCHAR(500));-- 文件备注

CREATE UNIQUE INDEX [pk_index_t_file]
  ON [t_file](
  [FileId]);


-- ------------------- (文件权限表) ---------------------
CREATE TABLE [t_file_permission](
  [PermId] UNIQUEIDENTIFIER PRIMARY KEY NOT NULL UNIQUE, -- 权限Id
  [FileId] UNIQUEIDENTIFIER NOT NULL, -- 文件Id
  [FilePath] VARCHAR(200), -- 文件路径
  [UserId] UNIQUEIDENTIFIER NOT NULL, -- 权限对应用户Id
  [CanWrite] INTEGER NOT NULL DEFAULT 0, -- 用户是否可写，0：不可写（默认），1：可写
  [CanRead] INTEGER NOT NULL DEFAULT 0);-- 用户是否可读，0：不可读（默认），1：可读

-- ------------------- (文件分享表) ---------------------
CREATE TABLE [t_file_share](
  [ShareId] UNIQUEIDENTIFIER PRIMARY KEY NOT NULL UNIQUE, -- 分享Id
  [FileId] UNIQUEIDENTIFIER NOT NULL, --分享文件Id
  [FilePath] VARCHAR(200), --分享文件路径
  [CreateUserId] UNIQUEIDENTIFIER NOT NULL, -- 分享创建用户Id
  [ShareCode] VARCHAR(20), -- 分享码
  [Password] VARCHAR(20), -- 密码
  [StartDate] TIMESTAMP, -- 开始日期
  [EndDate] TIMESTAMP);-- 结束日期

CREATE UNIQUE INDEX [pk_index_t_file_share]
  ON [t_file_share](
  [ShareId]);

-- ------------------- (用户表) ---------------------
CREATE TABLE [t_user](
  [Id] UNIQUEIDENTIFIER PRIMARY KEY NOT NULL UNIQUE, -- 用户Id
  [LoginName] VARCHAR(100) NOT NULL ON CONFLICT ABORT, -- 登录名
  [Password] VARCHAR(100) NOT NULL, -- 密码
  [PwdEncrypt] -- 密码是否加密
  [NickName] VARCHAR(50), -- 用户昵称
  [UserLogo] VARCHAR(300), -- 用户Logo
  [IsDel] INT NOT NULL DEFAULT 0, -- 用户是否被删除
  [CreateUserId] UNIQUEIDENTIFIER, -- 创建这个用户的用户Id
  [CreateDate] TIMESTAMP NOT NULL DEFAULT (DATETIME ()), -- 创建用户日期
  [UpdateDate] TIMESTAMP NOT NULL DEFAULT (DATETIME ()), -- 更新用户日期
  [Remark] VARCHAR(500));--用户备注

CREATE UNIQUE INDEX [pk_index_t_user]
  ON [t_user](
  [Id]);

-- ------------------- (操作记录表) ---------------------
CREATE TABLE [t_user_operate_log](
  [Id] UNIQUEIDENTIFIER PRIMARY KEY NOT NULL UNIQUE,-- 操作记录Id
  [OperateUserId] UNIQUEIDENTIFIER NOT NULL, -- 操作用户Id
  [OperateType] -- 1：登录 退出 新建文件 删除文件 分享文件 修改权限 创建用户 删除用户 修改用户
  [IP] -- IP地址
  [IPAddress] --IP对应的地理位置
  [OperateName] VARCHAR(200), --
  [FileId] UNIQUEIDENTIFIER, --操作文件Id，多个文件以逗号分隔
  [FilePath] VARCHAR(200), -- 操作文件路径
  [OperateDesc] VARCHAR(500), -- 操作描述
  [CreateDate] TIMESTAMP NOT NULL DEFAULT (DATETIME ()));--创建日期

CREATE UNIQUE INDEX [pk_index_t_user_operate]
  ON [t_user_operate](
  [Id]);