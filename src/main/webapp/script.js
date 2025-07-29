const dashboard_li = document.getElementById("dashboard-li");
const main_container = document.querySelector(".main-content");

const booking_li = document.getElementById("booking-li");
const rooms_li = document.getElementById("rooms-li");
const customer_li = document.getElementById("customer-li");
const invoice_li = document.getElementById("invoice-li");


document.querySelector(".dashboard-pannel").style.display="block";

document.querySelector(".cutomers").style.display="none";
document.querySelector(".invoices").style.display="none";




dashboard_li.addEventListener("click",()=>{
  // all none only dashboard(block)

  document.querySelector(".cutomers").style.display="none";
  document.querySelector(".invoices").style.display="none";
  document.querySelector(".dashboard-pannel").style.display="block";
 
})



customer_li.addEventListener("click",()=>{
  // all none except customer
  document.querySelector(".dashboard-pannel").style.display="none"

  document.querySelector(".invoices").style.display="none";
  document.querySelector(".cutomers").style.display="block";
})

invoice_li.addEventListener("click",()=>{
  document.querySelector(".booking-form").style.display="none";
  document.querySelector(".dashboard-pannel").style.display="none"
  document.querySelector(".rooms").style.display="none"
  document.querySelector(".cutomers").style.display="none";
  document.querySelector(".invoices").style.display="block";
})


