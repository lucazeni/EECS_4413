/**
 * 
 */
function validate() {
	var ok = true;
	var principal = document.getElementById("principal").value;
	var interest = document.getElementById("interest").value;
	var period = document.getElementById("period").value;

	if (isNaN(principal) || principal <= 0) {
		document.getElementById("principalError").innerHTML = "*";
		alert("Principal invalid!");
		ok = false;
	} else {
		document.getElementById("principalError").innerHTML = "";
	}

	if (isNaN(interest) || interest <= 0 || interest >= 100) {
		document.getElementById("interstError").innerHTML = "*";
		alert("Interest invalid!");
		ok = false;
	} else {
		document.getElementById("interstError").innerHTML = "";
	}

	if (isNaN(period) || period <= 0) {
		document.getElementById("periodError").innerHTML = "*";
		alert("Period invalid!");
		ok = false;
	} else {
		document.getElementById("periodError").innerHTML = "";
	}
	return ok;
}
	function doSimpleAjax(address){
		 if (validate() == true)
		{
		 var request = new XMLHttpRequest();
		 var data="?submit=ajax&";
		 var principal =document.querySelector("#principal").value;
		 var interest =document.querySelector("#interest").value;
		 var period =document.querySelector("#period").value;
		 var graceEnabled = document.querySelector("#graceEnabled").checked;
		 data += "principal=" + principal + "&interest=" + interest + "&period=" + period;
		 if(graceEnabled == true)
		 {	
			 graceEnabled = "on";
			 data += "&graceEnabled=" + graceEnabled;
		 }
		 request.open("GET", (address + data), true); // creates new request
		 request.onreadystatechange = function() { // event to be called when state changes
		 handler(request);
		 };
		 request.send(); //send() sends the request to the server.
		} 
	function handler(request){
		 if ((request.readyState == 4) && (request.status == 200)){
		 var target = document.querySelector("#ajaxTarget");
		 target.innerHTML = request.responseText;
		 }
	}
		} 
