/**
 * 
 */
function validate() {
	var ok = true;
	var p = document.getElementById("principal").value;
	if (isNaN(p) || p <= 0) {
		alert("Principal invalid!");
		ok = false;
	}
	p = document.getElementById("interest").value;
	if (isNaN(p) || p <= 0 || p >= 100) {
		alert("Interest invalid. Must be in [0,100].");
		ok = false;
	}
	p = document.getElementById("period").value;
	if (isNaN(p) || p <= 0) {
		alert("Period invalid!");
		ok = false;
	}
	return ok;
}