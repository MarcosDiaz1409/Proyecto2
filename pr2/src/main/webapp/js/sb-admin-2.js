(function($) {
  "use strict"; // Start of use strict

  // Toggle the side navigation
  $("#sidebarToggle, #sidebarToggleTop").on('click', function(e) {
    $("body").toggleClass("sidebar-toggled");
    $(".sidebar").toggleClass("toggled");
    if ($(".sidebar").hasClass("toggled")) {
      $('.sidebar .collapse').collapse('hide');
    };
  });

  // Close any open menu accordions when window is resized below 768px
  $(window).resize(function() {
    if ($(window).width() < 768) {
      $('.sidebar .collapse').collapse('hide');
    };
    
    // Toggle the side navigation when window is resized below 480px
    if ($(window).width() < 480 && !$(".sidebar").hasClass("toggled")) {
      $("body").addClass("sidebar-toggled");
      $(".sidebar").addClass("toggled");
      $('.sidebar .collapse').collapse('hide');
    };
  });

  // Prevent the content wrapper from scrolling when the fixed side navigation hovered over
  $('body.fixed-nav .sidebar').on('mousewheel DOMMouseScroll wheel', function(e) {
    if ($(window).width() > 768) {
      var e0 = e.originalEvent,
        delta = e0.wheelDelta || -e0.detail;
      this.scrollTop += (delta < 0 ? 1 : -1) * 30;
      e.preventDefault();
    }
  });

  // Scroll to top button appear
  $(document).on('scroll', function() {
    var scrollDistance = $(this).scrollTop();
    if (scrollDistance > 100) {
      $('.scroll-to-top').fadeIn();
    } else {
      $('.scroll-to-top').fadeOut();
    }
  });

  // Smooth scrolling using jQuery easing
  $(document).on('click', 'a.scroll-to-top', function(e) {
    var $anchor = $(this);
    $('html, body').stop().animate({
      scrollTop: ($($anchor.attr('href')).offset().top)
    }, 1000, 'easeInOutExpo');
    e.preventDefault();
  });
  
  //Evento del botón que me devuelve el listado de actores
  $("#btn-search-actors").click(function(){
		//alert("The button was clicked 1");
				
		$.ajax( {
			
			type: "GET",
			url: '/Proyecto2G2/HelloServlet',
			success: function(data) {
				//alert("Result" + data.resultado);
			    var htmlActorsList = '<ul>';
				$.each(data.actores, function(i,item){
					  htmlActorsList += '<li>' + item + '</li>';
				});
				htmlActorsList += '</ul>';
				$('#div-listado-actores').html("");
				$('#div-listado-actores').append(htmlActorsList);
			}
		} );
		
		
	});
	
	//Evento del botón que me devuelve el listado de generos
  $("#btn-search-genres").click(function(){
		//alert("The button was clicked 1");
				
		$.ajax( {
			
			type: "GET",
			url: '/Proyecto2G2/GenreServlet',
			success: function(data) {
				//alert("Result" + data.resultado);
			    var htmlGenresList = '<ul>';
				$.each(data.generos, function(i,item){
					  htmlGenresList += '<li>' + item + '</li>';
				});
				htmlGenresList += '</ul>';
				$('#div-listado-generos').html("");
				$('#div-listado-generos').append(htmlGenresList);
			}
		} );
		
		
	});

	
	//Evento del botón que me devuelve el listado de películas de un determinado actor
	$("#btn-movie-recommend-actor").click(function(){
				
		$.ajax( {
			
			type: "GET",
			url: '/Proyecto2G2/MoviesByActor?actor_name=' + $('#txt-rec-actor-name').val(),
			success: function(data) {
				//alert("Result" + data.resultado);
			    var htmlMovieList = '<ul>';
				$.each(data.peliculas, function(i,item){
					  htmlMovieList += '<li>' + item + '</li>';
				});
				htmlMovieList += '</ul>';
				$('#div-listado-recomendaciones-actor').html("");
				$('#div-listado-recomendaciones-actor').append(htmlMovieList);
			}
		} );
		
		
	});
	
	//Evento del botón que me devuelve el listado de películas de un determinado genero
	$("#btn-movie-recommend-genre").click(function(){
				
		$.ajax( {
			
			type: "GET",
			url: '/Proyecto2G2/MoviesByGenre?genre=' + $('#txt-rec-movie-genre').val(),
			success: function(data) {
				//alert("Result" + data.resultado);
			    var htmlGenreList = '<ul>';
				$.each(data.generos, function(i,item){
					  htmlGenreList += '<li>' + item + '</li>';
				});
				htmlGenreList += '</ul>';
				$('#div-listado-recomendaciones-genero').html("");
				$('#div-listado-recomendaciones-genero').append(htmlGenreList);
			}
		} );
		
		
	});

	
	//Evento del botón que creara una nueva pelicula
	$("#btn-movie-insert").click(function(){
		
		//alert("Resultado: " + '/Grupo20/SaveMovieServlet?title=' + $('#txt-movie-title').val() + '&release_year=' + $('#txt-movie-release_year').val() + '&tagline=' + $('#txt-movie-tagline').val());
				
		$.ajax( {
			
			type: "GET",
			url: '/Proyecto2G2/SaveMovieServlet?year=' + $('#txt-movie-year').val() + '&genre=' + $('#txt-movie-genre').val() + '&title=' + $('#txt-movie-title').val() ,
			success: function(data) {
			    alert("Resultado: " + data.resultado);
			}
		} );
		
		
	});
	
	//Evento del botón que eliminara una pelicula
	$("#btn-movie-delete").click(function(){
		
		//alert("Resultado: " + '/Grupo20/SaveMovieServlet?title=' + $('#txt-movie-title').val() + '&release_year=' + $('#txt-movie-release_year').val() + '&tagline=' + $('#txt-movie-tagline').val());
				
		$.ajax( {
			
			type: "GET",
			url: '/Proyecto2G2/DeleteMovieServlet?title=' + $('#txt-dlt-movie-title').val(),
			success: function(data) {
			    alert("Resultado: " + data.resultado);
			}
		} );
		
		
	});


})(jQuery); // End of use strict
