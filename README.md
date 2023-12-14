# Project-Work-Contacts
Simple contacts application for creating, reading, updating and deleting contacts.
Usage through a gui. Saved data includes personal id, first name, last name,
phone number, address(optional) and e-mail(optional)
All information saved to a text file which is stored persistently.

## Why?
Made as a student project for an introduction to programming course (Java)

## Compiling and running the application
Compile and run the application using following command in your CLI while inside project root:
cd src/ && javac *.java && java ContactsApp

## Saving contacts
To save a contact you must fill the following mandatory text fields:

Finnish ID number | example: 131052-308T

First Name | example: Jack

Last Name | example: Smith

Phone Number | example: +3584023423

Address and e-mail are optional. | address example: Hämeenkatu 1 | e-mail example: jack.smith@gmail.com

Address must have at least the name of the road and number of the building. "Hämeenkatu" wont work but the example given before does.
Can also be given more specifically for example: Hämeenkatu 1 A 232

Then click the "Save Contact" button.

## Viewing contacts
To view contacts click the "View Contacts" button and a new window will pop up.

## Updating existing contacts
To update contact information you must enter the personal id of the
contact you wish to update and then fill the same mandatory fields
that were used when saving the contact.

## Deleting existing contacts
To delete a contact you must enter the personal id of the contact
you wish to delete.

## Screenshots
<img src="https://i.imgur.com/AYJrHZ5.png"/>
<img src="https://i.imgur.com/4B0yHqB.png"/>

Link to API reference docs
https://docs.oracle.com/en/java/javase/17/docs/api/