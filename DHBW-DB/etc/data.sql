-- phpMyAdmin SQL Dump
-- version 3.3.7
-- http://www.phpmyadmin.net
--
-- Host: localhost
-- Generation Time: Mar 19, 2013 at 07:17 PM
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

CREATE TABLE IF NOT EXISTS `EMail` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `ReceiverMail` varchar(45) DEFAULT NULL,
  `SenderMail` varchar(45) DEFAULT NULL,
  `Header` varchar(45) DEFAULT NULL,
  `Body` varchar(45) DEFAULT NULL,
  `Date` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 AUTO_INCREMENT=1 ;

--
-- Dumping data for table `EMail`
--


-- --------------------------------------------------------

--
-- Table structure for table `Notebook`
--

CREATE TABLE IF NOT EXISTS `Notebook` (
  `ID` int(11) NOT NULL,
  `Name` varchar(45) NOT NULL,
  `IsDefective` bit(1) NOT NULL,
  `IsAvailable` bit(1) NOT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

--
-- Dumping data for table `Notebook`
--


-- --------------------------------------------------------

--
-- Table structure for table `OS`
--

CREATE TABLE IF NOT EXISTS `OS` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `Name` varchar(45) NOT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=MyISAM  DEFAULT CHARSET=utf8 AUTO_INCREMENT=6 ;

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

CREATE TABLE IF NOT EXISTS `Process` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `RequestID` varchar(45) NOT NULL,
  `RequesterID` int(11) NOT NULL,
  `ApproverID` int(11) NOT NULL,
  `NotebookID` int(11) NOT NULL,
  `Hash` varchar(32) NOT NULL,
  `CreationDate` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `StartDate` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `EndDate` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `UntilDate` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `StatusID` int(11) NOT NULL,
  `OSID` int(11) NOT NULL,
  `Description` varchar(240) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `UserID1` (`RequesterID`),
  KEY `UserID2` (`ApproverID`),
  KEY `NotebookID` (`NotebookID`),
  KEY `OSID` (`OSID`),
  KEY `StatusID` (`StatusID`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 AUTO_INCREMENT=1 ;

--
-- Dumping data for table `Process`
--


-- --------------------------------------------------------

--
-- Table structure for table `Status`
--

CREATE TABLE IF NOT EXISTS `Status` (
  `ID` int(11) NOT NULL,
  `Name` varchar(45) NOT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

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

CREATE TABLE IF NOT EXISTS `User` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `MatrNr` int(11) NOT NULL,
  `Vorname` varchar(45) NOT NULL,
  `Name` varchar(45) NOT NULL,
  `EMail` varchar(45) NOT NULL,
  `IsStudent` tinyint(4) NOT NULL,
  `IsAdmin` tinyint(4) NOT NULL,
  `IsLecturer` tinyint(4) NOT NULL,
  `Password` varchar(32) NOT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=MyISAM  DEFAULT CHARSET=utf8 AUTO_INCREMENT=17 ;

--
-- Dumping data for table `User`
--

INSERT INTO `User` (`ID`, `MatrNo`, `Firstname`, `Name`, `EMail`, `IsStudent`, `IsAdmin`, `IsLecturer`, `Password`) VALUES
(1, 100001, 'testuser1', 'Mueller', 'user1@dhbwstudent.com', 1, 0, 0, '6eda13efe1a69db8d7fa7c7d543ef9a5'),
(2, 100002, 'testuser2', 'Mueller', 'user2@dhbwstudent.com', 1, 0, 0, 'bf0da2e43d368123ed4de055ffcb934a'),
(3, 100003, 'testuser3', 'Mueller', 'user3@dhbwstudent.com', 1, 0, 0, 'e2d0b663499d6785b5a91783c74f9bee'),
(4, 100004, 'testuser4', 'Mueller', 'user4@dhbwstudent.com', 1, 0, 0, 'efff653302b79a530e4042a0bef6f39e'),
(5, 100005, 'testuser5', 'Mueller', 'user5@dhbwstudent.com', 1, 0, 0, '44963ba361be4db72f4241528b3f472f'),
(6, 100006, 'testuser6', 'Mueller', 'user6@dhbwstudent.com', 1, 0, 0, '977e841ac35af6250bfdea30667f7bac'),
(7, 100007, 'testuser7', 'Mueller', 'user7@dhbwstudent.com', 1, 0, 0, '5df2cb58225c54e8b8173754d24df318'),
(8, 100008, 'testuser8', 'Mueller', 'user8@dhbwstudent.com', 1, 0, 0, 'f3536a763fe6787ad6db084471070e23'),
(9, 100009, 'testuser9', 'Mueller', 'user9@dhbwstudent.com', 1, 0, 0, '70017656f4c9a162c82a526d31d0536c'),
(10, 1, 'Roland', 'Kuestermann', 'test1@dhbwlecturer.com', 0, 1, 0, 'caa66a072554b442f4e8e43c665924cb'),
(11, 2, 'Frederic', 'Toussaint', 'test2@dhbwlecturer.com', 0, 1, 0, 'caa66a072554b442f4e8e43c665924cb'),
(12, 3, 'Katja', 'Wengler', 'test3@dhbwlecturer.com', 0, 1, 0, 'caa66a072554b442f4e8e43c665924cb'),
(13, 4, 'Stefan', 'Klink', 'test4@dhbwlecturer.com', 0, 1, 0, 'caa66a072554b442f4e8e43c665924cb'),
(14, 5, 'Harald', 'Haake', 'test5@dhbwlecturer.com', 0, 1, 0, 'caa66a072554b442f4e8e43c665924cb'),
(15, 6, 'Super', 'Admin', 'adminAddress@gmail.com', 0, 0, 1, '531704a02607a1646efcf4c1fae1eec6'),
(16, 7, 'Jonathan', 'Osterman', 'drmanhatten@watchmen.com', 1, 1, 1, '17828ff61bd0ad2487e39a0d83d5e2bb');
