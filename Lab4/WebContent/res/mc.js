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