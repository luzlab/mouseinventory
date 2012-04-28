<%@page contentType="text/html;charset=UTF-8" language="java" %>
<%@page import="edu.ucsf.mousedatabase.HTMLGeneration" %>
<%=HTMLGeneration.getPageHeader(null,false,false, null) %>
<%=HTMLGeneration.getNavBar("search.jsp", false) %>

<script type="text/javascript">
$(document).ready(function(){
  $("#show_search_instructions").click(function(){
    $(".search-instructions").fadeToggle();
  });
})

</script>

<div class="pagecontent-centered">
  <div class="search-box centered">
    <form id="searchForm" action="handlemousesearch.jsp" class="form-search" method="get">
      <h2>Search for mice:</h2>
        <input type="text" class="input-xlarge search-query" name="searchterms" value="">
        <input class="btn" type="submit" value="Go">
    </form>
    <a href="#" id="show_search_instructions">help me search</a>
  </div>
  <div class="search-instructions">
    Enter search terms separated by spaces. 
    <br>
    
    Use "-" before a term to exclude it. Enclose exact phrase in quotes. Punctuation marks such as ,.?! will be ignored.
    <br><br>
    <b>Examples:</b>
    <br>
    cre hedgehog
    <dl><dd>Search for all records which <b>include both</b> cre <b>and</b> hedgehog</dd></dl>
    
    cre -hedgehog
    <dl><dd>Search for all records which <b>include</b> cre and do <b>NOT include</b> hedgehog</dd></dl>
    
    "fibroblast growth factor 10"
    <dl><dd>Search for all records which include the <b>exact phrase</b> fibroblast growth factor 10. Compare to unquoted search
        for same phrase which also returns <b>fibroblast growth factor</b> 9, MGI <b>10</b>4723 (Fgf9)
    </dd></dl>
    
    <b>IMPORTANT:</b> You may limit the search to mouse type by adding "Mutant Allele", "Transgenic", or "Inbred Strain" to your search terms.
  </div>
</div>

