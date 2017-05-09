// // Create and Deploy Your First Cloud Functions
// // https://firebase.google.com/docs/functions/write-firebase-functions
//
// exports.helloWorld = functions.https.onRequest((request, response) => {
//  response.send("Hello from Firebase!");
// });

const functions = require('firebase-functions');
const admin = require('firebase-admin');
admin.initializeApp(functions.config().firebase);

exports.allNotifications = functions.database.ref('/Notifications/{notif}').onWrite(event => {
	
	if (!event.data.exists()) {
        return;
    }
	
	const tokens = admin.database().ref('/Tokens').once('value');
	const notif = event.params.notif;
	
	return Promise.all([tokens]).then(results => {
		const tokensSnapshot = results[0];
		
		if(tokensSnapshot.hasChildren()) {
			
			const payload = {
			  notification: {
				title: 'Hackathon',
				body: notif
			  }
			};
			
			const listTokens = Object.keys(tokensSnapshot.val());

			return admin.messaging().sendToDevice(listTokens, payload).then(response => {
			  const tokensToRemove = [];
			  response.results.forEach((result, index) => {
				const error = result.error;
				if (error) {
				  console.error('Failure sending notification to', tokens[index], error);
				}
			  });
			  return Promise.all(tokensToRemove);
			});
			
		}
	});
	
});

exports.userNotifications = functions.database.ref('/NotificationsUser/{uid}/{notif}').onWrite(event => {
	
	if (!event.data.exists()) {
        return;
    }
	
	const uid = event.params.uid;
	const notif = event.params.notif;
	
	const payload = {
			  notification: {
				title: 'Hackathon',
				body: notif
			  }
			};
	
	admin.database().ref('/Tokens').once("value")
	  .then(function(snapshot) {
		snapshot.forEach(function(childSnapshot) {
		  var token = childSnapshot.key;
		  var tokenUid = childSnapshot.val();
		  
		  if(tokenUid === uid) {
			  
			  return admin.messaging().sendToDevice(token, payload)
				  .then(function(response) {
					console.log("Successfully sent message:", response);
				  })
				  .catch(function(error) {
					console.log("Error sending message:", error);
				  });
							  
				  console.log('eq UID: ', uid, 'TokenUID:', tokenUid);
			  }
		  });
	});
});