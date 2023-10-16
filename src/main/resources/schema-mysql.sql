CREATE TABLE IF NOT EXISTS `employee` (
  `employee_id` varchar(10) NOT NULL,
  `name` varchar(10) NOT NULL,
  `password` varchar(20) NOT NULL,
  `authorization_rank` int NOT NULL DEFAULT '1',
  `email` varchar(40) NOT NULL,
  `birthday` date NOT NULL,
  `registration_time` datetime NOT NULL,
  `activation` tinyint NOT NULL DEFAULT '0',
  PRIMARY KEY (`employee_id`)
);

CREATE TABLE IF NOT EXISTS `student` (
  `student_id` varchar(10) NOT NULL,
  `name` varchar(10) NOT NULL,
  `password` varchar(20) NOT NULL,
  `email` varchar(40) NOT NULL,
  `birthday` date NOT NULL,
  `registration_time` datetime NOT NULL,
  `activation` tinyint NOT NULL DEFAULT '0',
  `subsidy` tinyint NOT NULL DEFAULT '0',
  PRIMARY KEY (`student_id`)
);

CREATE TABLE IF NOT EXISTS `course` (
  `course_code` varchar(10) NOT NULL,
  `name` varchar(20) NOT NULL,
  `teacher` varchar(10) NOT NULL,
  `explanation` varchar(100) NOT NULL,
  `class_time_table` json NOT NULL,
  `course_start_date` date NOT NULL,
  `course_end_date` date NOT NULL,
  `on_shelf` tinyint NOT NULL,
  `course_position` varchar(10) NOT NULL,
  `build_time` datetime NOT NULL,
  `update_time` datetime NOT NULL,
  `update_employee` varchar(10) NOT NULL,
  PRIMARY KEY (`course_code`)
);

CREATE TABLE IF NOT EXISTS `student_course` (
  `student_id` varchar(10) NOT NULL,
  `name` varchar(10) NOT NULL,
  `selected_course` json DEFAULT NULL,
  PRIMARY KEY (`student_id`)
);

CREATE TABLE IF NOT EXISTS `course_schedule` (
  `id` int NOT NULL AUTO_INCREMENT,
  `course_code` varchar(10) NOT NULL,
  `course_date` date NOT NULL,
  `course_syllabus` varchar(100) NOT NULL,
  `course_program` varchar(20) NOT NULL,
  `course_overview` varchar(100) NOT NULL,
  PRIMARY KEY (`id`)
);

CREATE TABLE IF NOT EXISTS `location` (
  `location_id` varchar(20) NOT NULL,
  `location_name` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`location_id`)
);
