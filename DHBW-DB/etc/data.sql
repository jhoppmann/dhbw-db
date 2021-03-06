-- phpMyAdmin SQL Dump
-- version 3.3.7
-- http://www.phpmyadmin.net
--
-- Host: localhost
-- Generation Time: Mar 30, 2013 at 10:21 PM
-- Server version: 5.1.62
-- PHP Version: 5.3.3-pl1-gentoo

SET SQL_MODE="NO_AUTO_VALUE_ON_ZERO";

--
-- Database: `dhbw`
--

-- --------------------------------------------------------

--
-- Table structure for table `EMail`
--

DROP TABLE IF EXISTS `EMail`;
CREATE TABLE IF NOT EXISTS `EMail` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `ReceiverMail` varchar(150) DEFAULT NULL,
  `SenderMail` varchar(150) DEFAULT NULL,
  `Header` varchar(150) DEFAULT NULL,
  `Body` varchar(1500) DEFAULT NULL,
  `Date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`ID`)
) ENGINE=MyISAM  DEFAULT CHARSET=utf8 AUTO_INCREMENT=6 ;

--
-- Dumping data for table `EMail`
--


-- --------------------------------------------------------

--
-- Table structure for table `Notebook`
--

DROP TABLE IF EXISTS `Notebook`;
CREATE TABLE IF NOT EXISTS `Notebook` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `Name` varchar(45) NOT NULL,
  `IsDefective` bit(1) NOT NULL,
  `IsAvailable` bit(1) NOT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=27 ;

--
-- Dumping data for table `Notebook`
--

INSERT INTO `Notebook` (`ID`, `Name`, `IsDefective`, `IsAvailable`) VALUES
(1, 'HP Pavillion', 0, 1),
(2, 'HP Pavillion', 0, 1),
(3, 'HP Pavillion', 0, 1),
(4, 'HP Pavillion', 0, 1),
(5, 'HP Pavillion', 0, 1),
(6, 'Dell ProBook', 0, 1),
(7, 'Dell ProBood', 0, 1),
(8, 'Dell ProBood', 0, 1),
(9, 'Dell ProBood', 0, 1),
(10, 'Dell ProBood', 0, 1),
(11, 'Sony Vaio', 0, 1),
(12, 'Sony Vaio', 0, 1),
(13, 'Sony Vaio', 0, 1),
(14, 'Sony Vaio', 0, 1),
(15, 'Sony Vaio', 0, 1),
(16, 'One Superbook', 0, 1),
(17, 'One Superbook', 0, 1),
(18, 'One Superbook', 0, 1),
(19, 'One Superbook', 0, 1),
(20, 'One Superbook', 0, 1),
(21, 'Apple MacBook Pro', 0, 1),
(22, 'Apple MacBook Pro', 0, 1),
(23, 'Apple MacBook Pro', 0, 1),
(24, 'Apple MacBook Pro', 0, 1),
(25, 'Apple MacBook Pro', 0, 1);

-- --------------------------------------------------------

--
-- Table structure for table `NotebookCount`
--

DROP TABLE IF EXISTS `NotebookCount`;
CREATE TABLE IF NOT EXISTS `NotebookCount` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `Name` varchar(45) NOT NULL,
  `Count` int(11) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=MyISAM  DEFAULT CHARSET=utf8 AUTO_INCREMENT=4 ;

--
-- Dumping data for table `NotebookCount`
--

INSERT INTO `NotebookCount` (`ID`, `Name`, `Count`) VALUES
(1, 'LONG', 17),
(2, 'MEDIUM', 5),
(3, 'SHORT', 3);

-- --------------------------------------------------------

--
-- Table structure for table `OS`
--

DROP TABLE IF EXISTS `OS`;
CREATE TABLE IF NOT EXISTS `OS` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `Name` varchar(45) NOT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=6 ;

--
-- Dumping data for table `OS`
--

INSERT INTO `OS` (`ID`, `Name`) VALUES
(1, 'Windows 7'),
(2, 'Windows 8'),
(3, 'MacOS Mountain Lion'),
(4, 'Windows 3.11'),
(5, 'SUSE Linux 12.3');

-- --------------------------------------------------------

--
-- Table structure for table `Process`
--

DROP TABLE IF EXISTS `Process`;
CREATE TABLE IF NOT EXISTS `Process` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `RequesterID` int(11) DEFAULT NULL,
  `ApproverID` int(11) DEFAULT NULL,
  `NotebookID` int(11) DEFAULT NULL,
  `Hash` varchar(32) DEFAULT NULL,
  `CreationDate` timestamp NULL DEFAULT NULL,
  `StartDate` timestamp NULL DEFAULT NULL,
  `EndDate` timestamp NULL DEFAULT NULL,
  `UntilDate` timestamp NULL DEFAULT NULL,
  `StatusID` int(11) DEFAULT NULL,
  `OSID` int(11) DEFAULT NULL,
  `Description` varchar(240) DEFAULT NULL,
  `Category` varchar(32) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `UserID1` (`RequesterID`),
  KEY `UserID2` (`ApproverID`),
  KEY `NotebookID` (`NotebookID`),
  KEY `OSID` (`OSID`),
  KEY `StatusID` (`StatusID`)
) ENGINE=MyISAM  DEFAULT CHARSET=utf8 AUTO_INCREMENT=5 ;

--
-- Dumping data for table `Process`
--


-- --------------------------------------------------------

--
-- Table structure for table `Status`
--

DROP TABLE IF EXISTS `Status`;
CREATE TABLE IF NOT EXISTS `Status` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `Name` varchar(45) NOT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=9 ;

--
-- Dumping data for table `Status`
--

INSERT INTO `Status` (`ID`, `Name`) VALUES
(1, 'OPEN'),
(2, 'APPROVED'),
(3, 'RETRACTED'),
(4, 'REJECTED'),
(5, 'OVERDUE'),
(6, 'COMPLETED'),
(7, 'ERROR'),
(8, 'CANCELED');

-- --------------------------------------------------------

--
-- Table structure for table `User`
--

DROP TABLE IF EXISTS `User`;
CREATE TABLE IF NOT EXISTS `User` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `MatrNo` int(11) NOT NULL,
  `Firstname` varchar(45) NOT NULL,
  `Name` varchar(45) NOT NULL,
  `EMail` varchar(45) NOT NULL,
  `IsStudent` tinyint(4) NOT NULL,
  `IsAdmin` tinyint(4) NOT NULL,
  `IsLecturer` tinyint(4) NOT NULL,
  `Password` varchar(32) NOT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=17 ;

--
-- Dumping data for table `User`
--

INSERT INTO `User` (`ID`, `MatrNo`, `Firstname`, `Name`, `EMail`, `IsStudent`, `IsAdmin`, `IsLecturer`, `Password`) VALUES
(1, 100001, 'testuser1', 'Mueller', 'dhbw.notebook-verleih@gmx.de', 1, 0, 0, '6eda13efe1a69db8d7fa7c7d543ef9a5'),
(2, 100002, 'testuser2', 'Mueller', 'dhbw.notebook-verleih@gmx.de', 1, 0, 0, 'bf0da2e43d368123ed4de055ffcb934a'),
(3, 100003, 'testuser3', 'Mueller', 'dhbw.notebook-verleih@gmx.de', 1, 0, 0, 'e2d0b663499d6785b5a91783c74f9bee'),
(4, 100004, 'testuser4', 'Mueller', 'dhbw.notebook-verleih@gmx.de', 1, 0, 0, 'efff653302b79a530e4042a0bef6f39e'),
(5, 100005, 'testuser5', 'Mueller', 'dhbw.notebook-verleih@gmx.de', 1, 0, 0, '44963ba361be4db72f4241528b3f472f'),
(6, 100006, 'testuser6', 'Mueller', 'dhbw.notebook-verleih@gmx.de', 1, 0, 0, '977e841ac35af6250bfdea30667f7bac'),
(7, 100007, 'testuser7', 'Mueller', 'dhbw.notebook-verleih@gmx.de', 1, 0, 0, '5df2cb58225c54e8b8173754d24df318'),
(8, 100008, 'testuser8', 'Mueller', 'dhbw.notebook-verleih@gmx.de', 1, 0, 0, 'f3536a763fe6787ad6db084471070e23'),
(9, 100009, 'testuser9', 'Mueller', 'dhbw.notebook-verleih@gmx.de', 1, 0, 0, '70017656f4c9a162c82a526d31d0536c'),
(10, 1, 'Roland', 'Kuestermann', 'dhbw.notebook-verleih@gmx.de', 0, 0, 1, 'caa66a072554b442f4e8e43c665924cb'),
(11, 2, 'Frederic', 'Toussaint', 'dhbw.notebook-verleih@gmx.de', 0, 0, 1, 'caa66a072554b442f4e8e43c665924cb'),
(12, 3, 'Katja', 'Wengler', 'dhbw.notebook-verleih@gmx.de', 0, 0, 1, 'caa66a072554b442f4e8e43c665924cb'),
(13, 4, 'Stefan', 'Klink', 'dhbw.notebook-verleih@gmx.de', 0, 0, 1, 'caa66a072554b442f4e8e43c665924cb'),
(14, 5, 'Harald', 'Haake', 'dhbw.notebook-verleih@gmx.de', 0, 0, 1, 'caa66a072554b442f4e8e43c665924cb'),
(15, 6, 'Super', 'Admin', 'dhbw.notebook-verleih@gmx.de', 0, 1, 0, '531704a02607a1646efcf4c1fae1eec6'),
(16, 7, 'Jonathan', 'Osterman', 'dhbw.notebook-verleih@gmx.de', 1, 1, 1, '17828ff61bd0ad2487e39a0d83d5e2bb');
