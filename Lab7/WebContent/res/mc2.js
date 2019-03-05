function doSimpleAjax(address) {
	var request = new XMLHttpRequest();
	var data = "?submit=ajax&";
	var surname = document.querySelector("#surname").value;
	var mincredit = document.querySelector("#minCredit").value;
    document.getElementById("t").innerHTML = "";
	data += "surname=" + surname + "&minCredit=" + mincredit;
	request.open("GET", (address + data), true); // creates new request
	request.onreadystatechange = function() { // event to be called when
		// state changes
		handler(request);
	}
	request.send(); // send() sends the request to the server.

	function handler(request) {
		if ((request.readyState == 4) && (request.status == 200)) {
			var target = document.querySelector("#ajaxTarget");
			target.innerHTML = request.responseText;
		}
	}

}
